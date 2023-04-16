package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.InvalidServiceClientIdentifierRequestHeaderIdException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.MissingServiceClientIdentifierRequestHeaderException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.StaleServiceClientRequestException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedServiceClientIdentifierException;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.Set;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {
    @Inject private ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner;
    @Inject private ServiceRequestKeyService serviceRequestKeyService;

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
        String requestToken = request.getHeader(JdashboardInternalHttpRequestProperties.SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER);

        if (requestToken == null) {
            throw new MissingServiceClientIdentifierRequestHeaderException(String.format("Request url: %s", url));
        }

        ServiceRequestKey serviceRequestKey = serviceRequestKeyService.getByToken(requestToken).orElse(null);
        if (serviceRequestKey == null) {
            throw new InvalidServiceClientIdentifierRequestHeaderIdException(String.format("Request url: %s", url));
        }

        if (!authorizedServiceClientIdentifiers.contains(serviceRequestKey.getServiceClientName())) {
            throw new UnauthorizedServiceClientIdentifierException(String.format("Unrecognized service client identifier \"%s\". Request url: %s", serviceRequestKey.getServiceClientName(), url));
        }

        // If the request is older than the designated time-to-live, deny the request.
        if (Instant.now().isAfter(serviceRequestKey.getExpirationDate())) {
            throw new StaleServiceClientRequestException(String.format("Request url: %s", url));
        }
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
