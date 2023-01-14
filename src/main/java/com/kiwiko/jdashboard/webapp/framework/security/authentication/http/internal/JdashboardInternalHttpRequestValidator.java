package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.InvalidServiceClientIdentifierRequestHeaderIdException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.MissingServiceClientIdentifierRequestHeaderException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.StaleServiceClientRequestException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedServiceClientIdentifierException;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {

    @Inject private ApplicationEventService applicationEventService;
    @Inject private GsonProvider gsonProvider;
    @Inject private UniqueIdentifierService uniqueIdentifierService;

    @Override
    public void authorizeOutgoingRequest(URI uri, HttpRequest.Builder httpRequestBuilder, ApiRequest apiRequest) {
        RequestHeader serviceClientIdentifierRequestHeader = makeAuthorizedOutgoingRequestHeader(uri, apiRequest);
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

        String serviceClientIdentifier;
        try {
            String metadata = uniqueIdentifier.getReferenceKey();
            JsonObject json = JsonParser.parseString(metadata).getAsJsonObject();
            serviceClientIdentifier = Optional.ofNullable(json.get(JdashboardInternalHttpRequestProperties.UUID_SERVICE_CLIENT_IDENTIFIER_METADATA_REFERENCE_KEY_NAME))
                    .map(JsonElement::getAsString)
                    .orElse(null);
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("No service client identifier found for UUID ID %d. Request url: %s", uniqueIdentifier.getId(), url), e);
        }
        if (!authorizedServiceClientIdentifiers.contains(serviceClientIdentifier)) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("Unrecognized service client identifier \"%s\". Request url: %s", serviceClientIdentifier, url));
        }

        // If the request is older than the designated time-to-live, deny the request.
        if (Instant.now().isAfter(uniqueIdentifier.getCreatedDate().plus(JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_TOKEN_TTL))) {
            throw new StaleServiceClientRequestException(String.format("Request url: %s", url));
        }
    }

    private RequestHeader makeAuthorizedOutgoingRequestHeader(URI uri, ApiRequest apiRequest) {
        String url = uri.toString();
        ApplicationEvent requestEvent = ApplicationEvent.newBuilder(JdashboardInternalHttpRequestProperties.EVENT_TYPE_NAME)
                .setEventKey(url)
                .build();
        ApplicationEvent createdEvent = applicationEventService.create(requestEvent);

        String serviceClientId = Optional.ofNullable(apiRequest.getServiceClientIdentifier())
                // TODO isInternalServiceRequest is deprecated -- remove/replace all occurrences of it
                .orElseGet(() -> apiRequest.isInternalServiceRequest() ? JdashboardServiceClientIdentifiers.DEFAULT : null);

        RequestReferenceKeyMetadata metadata = RequestReferenceKeyMetadata.builder()
                // Provide an Application Event ID to ensure unique reference keys.
                .applicationEventId(createdEvent.getId())
                .serviceClientId(serviceClientId)
                .build();
        String referenceKey = gsonProvider.getDefault().toJson(metadata);
        CreateUuidParameters createUuidParameters = new CreateUuidParameters(referenceKey);
        UniversalUniqueIdentifier uniqueIdentifier = uniqueIdentifierService.create(createUuidParameters);

        return new RequestHeader(JdashboardInternalHttpRequestProperties.SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER, uniqueIdentifier.getUuid());
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
