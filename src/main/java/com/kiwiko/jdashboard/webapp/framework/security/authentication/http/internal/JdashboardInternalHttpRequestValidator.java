package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.InvalidServiceClientIdentifierRequestHeaderIdException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.MissingServiceClientIdentifierRequestHeaderException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.StaleServiceClientRequestException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedServiceClientIdentifierException;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {
    private static final Pattern UUID_REFERENCE_KEY_PATTERN = Pattern.compile("^applicationEventId:(?<applicationEventId>\\w+)$");

    @Inject private ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner;
    @Inject private ApplicationEventService applicationEventService;
    @Inject private GsonProvider gsonProvider;
    @Inject private UniqueIdentifierService uniqueIdentifierService;

    @Override
    public void authorizeOutgoingRequest(URI uri, HttpRequest.Builder httpRequestBuilder, ApiRequest apiRequest) throws ClientException {
        ProvisionServiceRequestKeyInput provisionServiceRequestKeyInput = ProvisionServiceRequestKeyInput.builder()
                .serviceClientIdentifier(apiRequest.getClientIdentifier())
                .description("Jdashboard internal service request (existing)")
                .expirationTime(Instant.now().plus(JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_TOKEN_TTL).toString())
                .build();

        ProvisionServiceRequestKeyOutput requestKey;
        try {
            requestKey = serviceCallRequestKeyProvisioner.provisionInternalServiceRequestKey(provisionServiceRequestKeyInput);
        } catch (ProvisionServiceRequestKeyException e) {
            throw new ClientException("Unable to authorize outgoing service request", e);
        }

        RequestHeader serviceClientIdentifierRequestHeader = new RequestHeader(requestKey.getRequestKeyHeaderName(), requestKey.getRequestKey());
        httpRequestBuilder.header(serviceClientIdentifierRequestHeader.getName(), serviceClientIdentifierRequestHeader.getValue());
    }

    @Override
    public void validateIncomingRequest(HttpServletRequest request, Set<String> authorizedServiceClientIdentifiers) throws UnauthorizedInternalRequestException {
        String url = getFullUrl(request);
        String uuid = request.getHeader(JdashboardInternalHttpRequestProperties.SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER);

        if (uuid == null) {
            throw new MissingServiceClientIdentifierRequestHeaderException(String.format("Request url: %s", url));
        }

        UniversalUniqueIdentifier uniqueIdentifier = uniqueIdentifierService.getByUuid(uuid).orElse(null);
        if (uniqueIdentifier == null) {
            throw new InvalidServiceClientIdentifierRequestHeaderIdException(String.format("Request url: %s", url));
        }

        ApplicationEvent requestEvent = Optional.ofNullable(uniqueIdentifier.getReferenceKey())
                .map(UUID_REFERENCE_KEY_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(matcher -> matcher.group("applicationEventId"))
                .map(Long::parseLong)
                .flatMap(applicationEventService::get)
                .orElseThrow(() -> new UnauthorizedServiceClientIdentifierException(String.format("Unable to determine request metadata for UUID ID %d. Request url: %s", uniqueIdentifier.getId(), url)));

        RequestKeyEventMetadata requestKeyMetadata;
        try {
            requestKeyMetadata = requestEvent.getMetadata()
                    .map(metadataJson -> gsonProvider.getDefault().fromJson(metadataJson, RequestKeyEventMetadata.class))
                    .orElseThrow(() -> new UnauthorizedServiceClientIdentifierException(String.format("Unable to determine request metadata for UUID ID %d. Request url: %s", uniqueIdentifier.getId(), url)));
            Objects.requireNonNull(requestKeyMetadata.getServiceClientId(), "Service client identifier is required to authorize incoming request");
            Objects.requireNonNull(requestKeyMetadata.getExpirationTime(), "Expiration time is required to authorize incoming request");
        } catch (JsonSyntaxException e) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("Unable to determine request metadata for UUID ID %d. Request url: %s", uniqueIdentifier.getId(), url), e);
        } catch (NullPointerException e) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("Invalid request metadata for UUID ID %d. Request url: %s", uniqueIdentifier.getId(), url), e);
        }

        updateApplicationEventMetadataWithUrl(requestEvent, requestKeyMetadata, url);

        if (!authorizedServiceClientIdentifiers.contains(requestKeyMetadata.getServiceClientId())) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("Unrecognized service client identifier \"%s\". Request url: %s", requestKeyMetadata.getServiceClientId(), url));
        }

        // If the request is older than the designated time-to-live, deny the request.
        if (Instant.now().isAfter(requestKeyMetadata.getExpirationTime())) {
            throw new StaleServiceClientRequestException(String.format("Request url: %s", url));
        }
    }

    private void updateApplicationEventMetadataWithUrl(ApplicationEvent requestEvent, RequestKeyEventMetadata metadata, String url) {
        metadata.setUrl(url);
        requestEvent.setMetadata(gsonProvider.getDefault().toJson(metadata));
        requestEvent.setIsRemoved(true);
        applicationEventService.update(requestEvent);
    }

    private String getFullUrl(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString != null) {
            builder.append('?').append(queryString);
        }

        return builder.toString();
    }
}
