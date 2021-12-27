package com.kiwiko.jdashboard.webapp.http.client.api.dto;

import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.PayloadSerializationException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface PayloadSerializer {

    @Nullable
    String serialize(Object payload) throws PayloadSerializationException;
}
