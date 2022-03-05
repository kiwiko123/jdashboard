package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class ClientResponse<T> {

    public static <E> ClientResponse<E> of(@Nonnull E payload) {
        Objects.requireNonNull(payload, "Payload is required");
        return new ClientResponse<>(payload, ResponseStatus.successful());
    }

    public static <E> ClientResponse<Optional<E>> ofNullable(@Nullable E payload) {
        return new ClientResponse<>(Optional.ofNullable(payload), ResponseStatus.successful());
    }

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
