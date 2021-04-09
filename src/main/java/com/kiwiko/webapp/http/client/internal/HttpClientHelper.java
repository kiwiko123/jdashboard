package com.kiwiko.webapp.http.client.internal;

import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.webapp.mvc.security.environments.api.EnvironmentService;

import javax.inject.Inject;

class HttpClientHelper {

    @Inject private EnvironmentService environmentService;

    <T extends HttpClientRequest> void setRequestData(T request) {
        String serverUri = environmentService.getServerURI().toString();
        String requestUrl = request.getUrl().trim();
        String url = normalizeUrl(serverUri, requestUrl);
        request.setUrl(url);
    }

    String normalizeUrl(String serverUrl, String path) {
        String normalizedPath = path.trim();
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = String.format("/%s", normalizedPath);
        }

        return String.format("%s%s", serverUrl, normalizedPath);
    }
}
