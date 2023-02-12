package com.kiwiko.jdashboard.tools.apiclient;

import javax.annotation.Nullable;

public class ClientResponse<T> {
    public static <E> ClientResponse<E> of(E data) {
        return new ClientResponse<E>(data);
    }

    public static <E> ClientResponse<E> unsuccessful(String errorCode, String errorMessage) {
        ClientResponseError error = new ClientResponseError(errorCode, errorMessage);
        return new ClientResponse<>(null, error);
    }

    private final T payload;
    private final @Nullable ClientResponseError error;

    public ClientResponse(T payload, ClientResponseError error) {
        this.payload = payload;
        this.error = error;
    }

    private ClientResponse(T payload) {
        this.payload = payload;
        error = null;
    }

    public T getPayload() {
        return payload;
    }

    @Nullable
    public ClientResponseError getError() {
        return error;
    }

    public boolean isSuccessful() {
        return error == null;
    }

    @Deprecated
    public ResponseStatus getStatus() {
        if (isSuccessful()) {
            return ResponseStatus.successful();
        }

        return isSuccessful() ? ResponseStatus.successful() : ResponseStatus.fromMessage(getError().getMessage());
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "payload=" + payload +
                ", error=" + error +
                '}';
    }
}
