package com.kiwiko.jdashboard.webapp.mvc.json.deserialization.api.interfaces;

import com.kiwiko.jdashboard.webapp.mvc.json.deserialization.api.interfaces.exceptions.RequestBodyDeserializationException;

import javax.annotation.Nullable;

public interface RequestBodyDeserializationStrategy {

    Object deserialize(@Nullable String requestBody, Class<?> targetType) throws RequestBodyDeserializationException;
}
