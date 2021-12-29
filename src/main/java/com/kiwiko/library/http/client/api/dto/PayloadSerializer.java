package com.kiwiko.library.http.client.api.dto;

import com.kiwiko.library.http.client.api.exceptions.PayloadSerializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadSerializer {

    @Nullable
    String serialize(Object payload) throws PayloadSerializationException;
}
