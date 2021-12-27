package com.kiwiko.jdashboard.webapp.http.client.api.dto;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.PayloadSerializationException;

import javax.annotation.Nullable;

public class DefaultGsonPayloadSerializer extends DefaultGsonProvider implements PayloadSerializer {

    @Nullable
    @Override
    public String serialize(Object payload) throws PayloadSerializationException {
        if (payload == null) {
            return null;
        }

        try {
            return getGson().toJson(payload);
        } catch (JsonSyntaxException e) {
            throw new PayloadSerializationException(String.format("Failed to deserialize payload %s", payload), e);
        }
    }
}
