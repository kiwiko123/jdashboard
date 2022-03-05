package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Objects;
import java.util.Optional;

public class ApiClientRequestHelper {

    @Inject private EnvironmentService environmentService;
    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;

    public void validateRequest(ApiRequest request) {
        Objects.requireNonNull(request.getRequestUrl(), "Request URL is required");
        Objects.requireNonNull(request.getRequestMethod(), "Request method is required");
    }

    public HttpRequest makeHttpRequest(ApiRequest apiRequest) throws ClientException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();

        switch (apiRequest.getRequestMethod()) {
            case GET:
                builder.GET();
                break;
            case POST:
                builder.POST(makeBodyPublisher(apiRequest));
                break;
            case PUT:
                builder.PUT(makeBodyPublisher(apiRequest));
                break;
            case DELETE:
                builder.DELETE();
                break;
        }

        URI uri = toUri(apiRequest);
        builder.uri(uri);

        apiRequest.getRequestHeaders()
                .forEach(header -> builder.header(header.getName(), header.getValue()));

        if (apiRequest.isInternalServiceRequest()) {
            internalHttpRequestValidator.authorizeOutgoingRequest(uri, builder);
        }

        Optional.ofNullable(apiRequest.getRequestTimeout())
                .ifPresent(builder::timeout);

        return builder.build();
    }

    private HttpRequest.BodyPublisher makeBodyPublisher(ApiRequest apiRequest) throws ClientException {
        Object requestBody = apiRequest.getRequestBody();
        if (requestBody == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        String bodyString = apiRequest.getRequestBodySerializer().serialize(requestBody);
        if (bodyString == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        return HttpRequest.BodyPublishers.ofString(bodyString);
    }

    private URI toUri(ApiRequest apiRequest) throws ClientException {
        RequestUrl requestUrl = apiRequest.getRequestUrl();
        if (requestUrl.getUri() != null) {
            return requestUrl.getUri();
        }

        UriBuilder uriBuilder = requestUrl.getUriBuilder();
        Objects.requireNonNull(uriBuilder, "Partial URI is required");

        if (uriBuilder.getScheme() == null && uriBuilder.getHost() == null) {
            // If the scheme and host are absent,
            // assume the request's destination is inside Jdashboard and infer their values.
            URI serverUri = environmentService.getServerURI();

            uriBuilder.setScheme(serverUri.getScheme())
                    .setHost(serverUri.getHost())
                    .setPort(serverUri.getPort());
        }

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new ClientException(String.format("Malformed URL %s", uriBuilder), e);
        }
    }
}
