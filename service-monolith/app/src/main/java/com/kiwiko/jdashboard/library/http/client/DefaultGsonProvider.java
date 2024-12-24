package com.kiwiko.jdashboard.library.http.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.gson.adapters.InstantIso8061Adapter;

import java.time.Instant;

class DefaultGsonProvider {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantIso8061Adapter())
            .create();

    protected Gson getGson() {
        return GSON;
    }
}
