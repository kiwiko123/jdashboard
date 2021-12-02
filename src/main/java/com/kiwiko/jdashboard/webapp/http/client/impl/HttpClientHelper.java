package com.kiwiko.jdashboard.webapp.http.client.impl;

import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;

import javax.inject.Inject;

class HttpClientHelper {

    @Inject private EnvironmentService environmentService;
    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;

    <T extends HttpClientRequest> void setRequestData(T request) {
        setUrl(request);
        setInternalServiceData(request);
    }

    private <T extends HttpClientRequest> void setUrl(T request) {
        String serverUri = environmentService.getServerURI().toString();
        String requestUrl = request.getUrl().trim();
        String url = normalizeUrl(serverUri, requestUrl);
        request.setUrl(url);
    }

    private String normalizeUrl(String serverUrl, String path) {
        String normalizedPath = path.trim();
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = String.format("/%s", normalizedPath);
        }

        return String.format("%s%s", serverUrl, normalizedPath);
    }

    private <T extends HttpClientRequest> void setInternalServiceData(T request) {
        if (!request.isInternalServiceRequest()) {
            return;
        }
        internalHttpRequestValidator.authorizeOutgoingRequest(request);
    }
}
