package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.exceptions.PayloadDeserializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadDeserializer {

    @Nullable
    <T> T deserialize(@Nullable String payload, Class<T> resultType) throws PayloadDeserializationException;
}
