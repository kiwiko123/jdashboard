package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientRuntimeException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.DefaultJdashboardApiClientPlugins;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ApiClientResponseHelper {
    @Inject private DefaultJdashboardApiClientPlugins defaultJdashboardApiClientPlugins;

    private Set<RequestHeader> makeHeaders(HttpResponse<?> httpResponse) {
        Set<RequestHeader> headers = new HashSet<>();
        httpResponse.headers().map().forEach((header, values) -> {
            values.stream()
                    .map(value -> new RequestHeader(header, value))
                    .forEach(headers::add);
        });

        return Collections.unmodifiableSet(headers);
    }

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
            ApiResponse<ResponseType> convertHttpResponse(RequestType request, RequestContextType requestContext, HttpResponse<String> httpResponse) throws ClientException, ServerException {
        validateHttpStatusCode(httpResponse);

        @SuppressWarnings("unchecked")
        Class<ResponseType> responseType = (Class<ResponseType>) requestContext.getResponseType();
        Set<RequestHeader> responseHeaders = makeHeaders(httpResponse);

        ResponseType payload;

        if (responseType == null) {
            payload = null;
        } else if (responseType == String.class) {
            payload = (ResponseType) (httpResponse.body());
        } else {
            String body = httpResponse.body();
            payload = requestContext.getResponseBodyDeserializer().deserialize(body, responseType);
        }

        return new ApiResponse<>(
                payload,
                httpResponse.statusCode(),
                responseHeaders,
                httpResponse.uri().toString());
    }

    @SuppressWarnings("unchecked")
    public <ResponseType> ApiResponse<ResponseType> convertHttpResponse(
            ApiRequest apiRequest,
            HttpResponse<String> httpResponse) throws ClientException, ServerException {
        apiRequest.getRequestErrorHandler().handleError(httpResponse);

        int status = httpResponse.statusCode();
        Class<ResponseType> responseType = (Class<ResponseType>) apiRequest.getResponseType();

        Set<RequestHeader> responseHeaders = makeHeaders(httpResponse);
        ResponseType payload;

        if (responseType == null) {
            payload = null;
        } else if (responseType == String.class) {
            payload = (ResponseType) (httpResponse.body());
        } else {
            String body = httpResponse.body();
            payload = apiRequest.getResponsePayloadDeserializer().deserialize(body, responseType);
        }

        return new ApiResponse<>(
                payload,
                status,
                responseHeaders,
                httpResponse.uri().toString());
    }

    public <T> CompletableFuture<ApiResponse<T>> transformResponseFuture(
            ApiRequest apiRequest,
            CompletableFuture<HttpResponse<String>> responseFuture) throws ClientException {
        return responseFuture.thenApply(httpResponse -> {
            try {
                return convertHttpResponse(apiRequest, httpResponse);
            } catch (Exception e) {
                throw new ApiClientRuntimeException(e);
            }
        });
    }

    private void validateHttpStatusCode(HttpResponse<?> httpResponse) throws ClientException, ServerException {
        HttpStatus httpStatus = HttpStatus.valueOf(httpResponse.statusCode());
        if (httpStatus.is4xxClientError()) {
            throw new ClientException(String.format("Request failed with HTTP status %s: %s", httpStatus, httpResponse));
        }

        if (httpStatus.is5xxServerError()) {
            throw new ServerException(String.format("Request failed with HTTP status %s: %s", httpStatus, httpResponse));
        }
    }

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        void runPostRequestPlugins(RequestType request, RequestContextType requestContext, ApiResponse<ResponseType> apiResponse) throws ClientException, ServerException {
        List<PostRequestPlugin> postRequestPlugins = requestContext.getPostRequestPlugins().getPlugins().stream()
                .distinct()
                .toList();
        if (requestContext.shouldEnableDefaultRequestPlugins()) {
            List<PostRequestPlugin> customPostRequestPlugins = requestContext.getPostRequestPlugins().getPlugins();
            List<PostRequestPlugin> defaultPostRequestPlugins = defaultJdashboardApiClientPlugins.getPostRequestPlugins().getPlugins();

            postRequestPlugins = Stream.concat(customPostRequestPlugins.stream(), defaultPostRequestPlugins.stream())
                    .distinct()
                    .toList();
        }

        for (PostRequestPlugin postRequestPlugin : postRequestPlugins) {
            postRequestPlugin.postRequest(request, requestContext, apiResponse);
        }
    }
}
