package com.kiwiko.jdashboard.webapp.http.client.impl.apiclient;

import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.JdashboardApiClientException;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JdashboardHttpApiClient implements JdashboardApiClient {

    @Inject private HttpClientHelper httpClientHelper;
    @Inject private EnvironmentService environmentService;

    @Override
    public <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException {
        validateRequest(request);
        HttpRequest httpRequest = makeHttpRequest(request);
        HttpResponse<String> httpResponse = httpClientHelper.sendSynchronousRequest(httpRequest);

        return convertHttpResponse(request, httpResponse);
    }

    @Override
    public <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException {
        validateRequest(request);
        return null;
    }

    private void validateRequest(ApiRequest request) {
        Objects.requireNonNull(request.getRequestMethod(), "Request method is required");
        Objects.requireNonNull(request.getUrl(), "URL is required");
    }

    private HttpRequest makeHttpRequest(ApiRequest apiRequest) throws ClientException {
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

        builder.uri(toUri(makeUrl(apiRequest)));

        apiRequest.getRequestHeaders()
                .forEach(header -> builder.header(header.getName(), header.getValue()));

        Optional.ofNullable(apiRequest.getRequestTimeout())
                .ifPresent(builder::timeout);

        return builder.build();
    }

    private HttpRequest.BodyPublisher makeBodyPublisher(ApiRequest apiRequest) throws ClientException {
        Object requestBody = apiRequest.getRequestBody();
        if (requestBody == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        String bodyString = apiRequest.getPayloadSerializer().serialize(requestBody);
        if (bodyString == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        return HttpRequest.BodyPublishers.ofString(bodyString);
    }

    private String normalizeUrl(String serverUrl, String path) {
        String normalizedPath = path.trim();
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = String.format("/%s", normalizedPath);
        }

        return String.format("%s%s", serverUrl, normalizedPath);
    }

    private String makeUrl(ApiRequest apiRequest) throws ClientException {
        String rawUrl = apiRequest.getUrl();

        if (!apiRequest.isRelativeUrl()) {
            return rawUrl;
        }

        String serverUri = environmentService.getServerURI().toString();
        String requestUrl = rawUrl.trim();
        return normalizeUrl(serverUri, requestUrl);
    }

    private URI toUri(String url) throws ClientException {
        // TODO relative url
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new ClientException(String.format("Malformed URL %s", url), e);
        }
    }

    private Set<RequestHeader> makeHeaders(HttpResponse<?> httpResponse) {
        Set<RequestHeader> headers = new HashSet<>();
        httpResponse.headers().map().forEach((header, values) -> {
            values.stream()
                    .map(value -> new RequestHeader(header, value))
                    .forEach(headers::add);
        });

        return headers;
    }

    @SuppressWarnings("unchecked")
    private <ResponseType> ApiResponse<ResponseType> convertHttpResponse(
            ApiRequest apiRequest,
            HttpResponse<String> httpResponse) throws ClientException {
        Class<ResponseType> responseType = apiRequest.getResponseType();

        Set<RequestHeader> responseHeaders = makeHeaders(httpResponse);
        int status = httpResponse.statusCode();
        ResponseType payload;

        if (responseType == null) {
            payload = null;
        } else if (responseType == String.class) {
            payload = (ResponseType) (httpResponse.body());
        } else {
            payload = apiRequest.getPayloadDeserializer().deserialize(httpResponse.body(), responseType);
        }

        return new ApiResponse<>(
                payload,
                status,
                responseHeaders,
                httpResponse.uri().toString());
    }
}
