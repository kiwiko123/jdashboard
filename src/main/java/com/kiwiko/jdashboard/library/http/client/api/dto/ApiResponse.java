package com.kiwiko.jdashboard.library.http.client.api.dto;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

public class ApiResponse<T> {
    private final @Nullable T payload;
    private final int httpStatusCode;
    private final Set<RequestHeader> requestHeaders;
    private final String url;

    public ApiResponse(
            @Nullable T payload,
            int httpStatusCode,
            Set<RequestHeader> requestHeaders,
            String url) {
        this.payload = payload;
        this.httpStatusCode = httpStatusCode;
        this.requestHeaders = requestHeaders;
        this.url = url;
    }

    @Nullable
    public T getPayload() {
        return payload;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public Set<RequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiResponse<?> that = (ApiResponse<?>) o;
        return httpStatusCode == that.httpStatusCode
                && Objects.equals(payload, that.payload)
                && Objects.equals(requestHeaders, that.requestHeaders)
                && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, httpStatusCode);
    }
}
