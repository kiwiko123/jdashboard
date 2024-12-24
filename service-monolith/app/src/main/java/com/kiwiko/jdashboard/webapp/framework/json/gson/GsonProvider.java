package com.kiwiko.jdashboard.webapp.framework.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.gson.adapters.InstantIso8061Adapter;

import java.time.Instant;

public class GsonProvider {
    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantIso8061Adapter())
            .create();

    public GsonBuilder builder() {
        return new GsonBuilder();
    }

    public Gson getDefault() {
        return DEFAULT_GSON;
    }
}
