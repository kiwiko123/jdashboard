package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.DefaultJdashboardApiClientPlugins;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;
import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ApiClientRequestHelper {

    @Inject private EnvironmentService environmentService;
    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;
    @Inject private DefaultJdashboardApiClientPlugins defaultJdashboardApiClientPlugins;

    public void validateRequest(HttpApiRequest request) throws ClientException {
        try {
            Objects.requireNonNull(request.getRequestUrl(), "Request URL is required");
            Objects.requireNonNull(request.getRequestMethod(), "Request method is required");
        } catch (NullPointerException e) {
            throw new ClientException("Request object failed validation", e);
        }
    }

    public void validateRequest(ApiRequest request) throws ClientException {
        try {
            Objects.requireNonNull(request.getRequestUrl(), "Request URL is required");
            Objects.requireNonNull(request.getRequestMethod(), "Request method is required");
            Objects.requireNonNull(request.getRequestErrorHandler(), "Request error handler is required");
        } catch (NullPointerException e) {
            throw new ClientException("Request object failed validation", e);
        }
    }

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        HttpRequest makeHttpRequest(RequestType request, RequestContextType requestContext) throws ClientException {
        // Run pre-request plugins first because they may mutate the request/context state.
        runPreRequestPlugins(request, requestContext);

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder();

        switch (request.getRequestMethod()) {
            case GET:
                httpRequestBuilder.GET();
                break;
            case POST:
                httpRequestBuilder.POST(makeBodyPublisher(request, requestContext));
                break;
            case PUT:
                httpRequestBuilder.PUT(makeBodyPublisher(request, requestContext));
                break;
            case DELETE:
                httpRequestBuilder.DELETE();
                break;
            default:
                throw new ClientException(String.format("Unsupported request method: %s", request.getRequestMethod()));
        }

        URI uri = toUri(request.getRequestUrl());
        httpRequestBuilder.uri(uri);

        request.getRequestHeaders()
                .forEach(header -> httpRequestBuilder.header(header.getName(), header.getValue()));

        Optional.ofNullable(requestContext.getRequestTimeout())
                .ifPresent(httpRequestBuilder::timeout);

        return httpRequestBuilder.build();
    }

    private <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        void runPreRequestPlugins(RequestType request, RequestContextType requestContext) throws ClientException {
        List<PreRequestPlugin> preRequestPlugins = requestContext.getPreRequestPlugins().getPlugins().stream()
                .distinct()
                .toList();
        if (requestContext.shouldEnableDefaultRequestPlugins()) {
            List<PreRequestPlugin> customPreRequestPlugins = requestContext.getPreRequestPlugins().getPlugins();
            List<PreRequestPlugin> defaultPreRequestPlugins = defaultJdashboardApiClientPlugins.getPreRequestPlugins().getPlugins();

            preRequestPlugins = Stream.concat(customPreRequestPlugins.stream(), defaultPreRequestPlugins.stream())
                    .distinct()
                    .toList();
        }

        for (PreRequestPlugin preRequestPlugin : preRequestPlugins) {
            preRequestPlugin.preRequest(request, requestContext);
        }
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

        URI uri = toUri(apiRequest.getRequestUrl());
        builder.uri(uri);

        apiRequest.getRequestHeaders()
                .forEach(header -> builder.header(header.getName(), header.getValue()));

        if (apiRequest.getClientIdentifier() != null) {
            internalHttpRequestValidator.authorizeOutgoingRequest(uri, builder, apiRequest);
        }

        Optional.ofNullable(apiRequest.getRequestTimeout())
                .ifPresent(builder::timeout);

        return builder.build();
    }

    private <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        HttpRequest.BodyPublisher makeBodyPublisher(RequestType request, RequestContextType requestContext) throws ClientException {
        Object requestBody = request.getRequestBody();
        if (requestBody == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        String bodyString = requestContext.getRequestBodySerializer().serialize(requestBody);
        if (bodyString == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        return HttpRequest.BodyPublishers.ofString(bodyString);
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

    private URI toUri(RequestUrl requestUrl) throws ClientException {
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
