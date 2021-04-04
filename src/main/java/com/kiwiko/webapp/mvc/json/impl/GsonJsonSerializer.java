package com.kiwiko.webapp.mvc.json.impl;

import com.google.gson.Gson;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;

public class GsonJsonSerializer implements JsonSerializer {
    private static final Gson DEFAULT_GSON = new Gson();

    protected Gson getGson() {
        return DEFAULT_GSON;
    }

    @Override
    public String toJson(Object value) {
        return getGson().toJson(value);
    }

    @Override
    public <T> T fromJson(String json, Class<T> responseType) {
        return getGson().fromJson(json, responseType);
    }
}
