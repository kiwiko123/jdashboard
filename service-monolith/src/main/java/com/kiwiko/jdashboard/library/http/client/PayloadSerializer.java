package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.exceptions.PayloadSerializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadSerializer {

    @Nullable
    String serialize(Object payload) throws PayloadSerializationException;
}
