package com.kiwiko.jdashboard.webapp.mvc.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private static final Gson DEFAULT_GSON = new Gson();

    public GsonBuilder builder() {
        return new GsonBuilder();
    }

    public Gson getDefault() {
        return DEFAULT_GSON;
    }
}
