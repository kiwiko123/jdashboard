package com.kiwiko.library.http.client.internal.serialization;

import com.google.gson.Gson;

public class GsonRequestSerializer implements RequestSerializer {
    private static final Gson DEFAULT_GSON = new Gson();

    @Override
    public String toJson(Object value) {
        return DEFAULT_GSON.toJson(value);
    }

    @Override
    public <T> T fromJson(String json, Class<T> responseType) {
        return DEFAULT_GSON.fromJson(json, responseType);
    }
}
