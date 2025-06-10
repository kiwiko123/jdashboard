package com.kiwiko.jdashboard.http.client.serialize;

import com.kiwiko.jdashboard.http.client.exception.PayloadSerializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadSerializer {

    @Nullable
    String serialize(Object payload) throws PayloadSerializationException;
}
