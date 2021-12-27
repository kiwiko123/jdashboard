package com.kiwiko.jdashboard.webapp.http.client.impl.apiclient;

import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.ClientException;

import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

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
            HttpResponse<String> httpResponse) throws ClientException {
        int status = httpResponse.statusCode();
        Class<ResponseType> responseType = apiRequest.getResponseType();

        Set<RequestHeader> responseHeaders = makeHeaders(httpResponse);
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
