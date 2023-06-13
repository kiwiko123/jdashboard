package com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.interfaces.RequestBodyDeserializationStrategy;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.interfaces.exceptions.RequestBodyDeserializationException;

import javax.annotation.Nullable;

public class GsonRequestBodyDeserializationStrategy implements RequestBodyDeserializationStrategy {
    private static final Gson DEFAULT_GSON = new Gson();

    @Override
    public Object deserialize(@Nullable String requestBody, Class<?> targetType) throws RequestBodyDeserializationException {
        Gson gson = getGson();
        try {
            return gson.fromJson(requestBody, targetType);
        } catch (JsonSyntaxException e) {
            throw new RequestBodyDeserializationException(String.format("Error deserializing request body %s", requestBody), e);
        }
    }

    protected Gson getGson() {
        return DEFAULT_GSON;
    }
}
