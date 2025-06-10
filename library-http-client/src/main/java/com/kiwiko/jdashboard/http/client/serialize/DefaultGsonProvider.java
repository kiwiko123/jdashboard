package com.kiwiko.jdashboard.http.client.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;

class DefaultGsonProvider {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantIso8061Adapter())
            .create();

    protected Gson getGson() {
        return GSON;
    }
}
