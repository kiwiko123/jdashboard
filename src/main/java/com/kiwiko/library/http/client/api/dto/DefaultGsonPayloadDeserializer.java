package com.kiwiko.library.http.client.api.dto;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.library.http.client.api.exceptions.PayloadDeserializationException;

import javax.annotation.Nullable;

public class DefaultGsonPayloadDeserializer extends DefaultGsonProvider implements PayloadDeserializer {

    @Nullable
    @Override
    public <T> T deserialize(@Nullable String payload, Class<T> resultType) throws PayloadDeserializationException {
        if (payload == null) {
            return null;
        }

        try {
            return getGson().fromJson(payload, resultType);
        } catch (JsonSyntaxException e) {
            throw new PayloadDeserializationException(String.format("Failed to deserialize payload %s", payload), e);
        }
    }
}
