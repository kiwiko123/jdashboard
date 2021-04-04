package com.kiwiko.library.http.client.dto;

public class HttpClientResponse<T> {
    private final int status;
    private final T payload;

    public HttpClientResponse(int status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public int getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }
}
