package com.kiwiko.jdashboard.tools.apiclient.api.dto;

public class ClientResponse<T> {

    private final T payload;
    private final ResponseStatus status;

    public ClientResponse(T payload, ResponseStatus status) {
        this.payload = payload;
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "payload=" + payload +
                ", status=" + status +
                '}';
    }
}
