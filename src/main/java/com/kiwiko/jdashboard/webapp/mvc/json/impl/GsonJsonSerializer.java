package com.kiwiko.jdashboard.webapp.mvc.json.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.webapp.mvc.json.api.JsonSerializer;
import com.kiwiko.jdashboard.webapp.mvc.json.gson.adapters.InstantTextAdapter;

import java.time.Instant;

public class GsonJsonSerializer implements JsonSerializer {
    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantTextAdapter())
            .create();

    public Gson gson() {
        return DEFAULT_GSON;
    }

    @Override
    public String toJson(Object value) {
        return gson().toJson(value);
    }

    @Override
    public <T> T fromJson(String json, Class<T> responseType) throws JsonSyntaxException {
        return gson().fromJson(json, responseType);
    }
}
