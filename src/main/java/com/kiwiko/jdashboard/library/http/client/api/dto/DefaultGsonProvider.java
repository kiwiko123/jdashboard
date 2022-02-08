package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.gson.adapters.InstantTextAdapter;

import java.time.Instant;

class DefaultGsonProvider {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantTextAdapter())
            .create();

    protected Gson getGson() {
        return GSON;
    }
}
