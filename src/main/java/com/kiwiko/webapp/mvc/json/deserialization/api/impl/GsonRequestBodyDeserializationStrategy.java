package com.kiwiko.webapp.mvc.json.deserialization.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.webapp.mvc.json.deserialization.api.interfaces.RequestBodyDeserializationStrategy;
import com.kiwiko.webapp.mvc.json.deserialization.api.interfaces.exceptions.RequestBodyDeserializationException;
import com.kiwiko.webapp.mvc.json.gson.GsonProvider;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class GsonRequestBodyDeserializationStrategy implements RequestBodyDeserializationStrategy {

    @Override
    public Object deserialize(@Nullable String requestBody, Class<?> targetType) throws RequestBodyDeserializationException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(requestBody, targetType);
        } catch (JsonSyntaxException e) {
            throw new RequestBodyDeserializationException(String.format("Error deserializing request body %s", requestBody), e);
        }
    }
}
