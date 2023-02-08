package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientRuntimeException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;

import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ApiClientResponseHelper {

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
}
