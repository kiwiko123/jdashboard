package com.kiwiko.jdashboard.framework.http.client.impl.apiclient;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientRuntimeException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ApiClientResponseHelper {

    @Inject private ApiClientCache apiClientCache;

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
        validateResponse(httpResponse);

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

        ApiResponse<ResponseType> response = new ApiResponse<>(
                payload,
                status,
                responseHeaders,
                httpResponse.uri().toString());

        apiClientCache.processRequestCaching(apiRequest, response);

        return response;
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

    private void validateResponse(HttpResponse<String> httpResponse) throws ClientException, ServerException {
        validateHttpStatus(httpResponse);
    }

    private void validateHttpStatus(HttpResponse<String> httpResponse) throws ClientException, ServerException {
        HttpStatus httpStatus = HttpStatus.valueOf(httpResponse.statusCode());
        if (httpStatus.is4xxClientError()) {
            throw new ClientException(String.format("Request failed with HTTP status %s: %s", httpStatus, httpResponse));
        }

        if (httpStatus.is5xxServerError()) {
            throw new ServerException(String.format("Request failed with HTTP status %s: %s", httpStatus, httpResponse));
        }
    }
}
