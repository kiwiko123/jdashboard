package com.kiwiko.library.http.client.dto;

public class HttpClientResponse<T> {
    private final int status;
    private final T payload;
    private final ResponseMetadata metadata;

    public HttpClientResponse(int status, T payload, ResponseMetadata metadata) {
        this.status = status;
        this.payload = payload;
        this.metadata = metadata;
    }

    public int getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public ResponseMetadata getMetadata() {
        return metadata;
    }
}
