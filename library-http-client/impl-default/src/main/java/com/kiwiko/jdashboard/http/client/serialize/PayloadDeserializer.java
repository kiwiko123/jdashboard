package com.kiwiko.jdashboard.http.client.serialize;

import com.kiwiko.jdashboard.http.client.exception.PayloadDeserializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadDeserializer {

    @Nullable
    <T> T deserialize(@Nullable String payload, Class<T> resultType)
            throws PayloadDeserializationException;
}
