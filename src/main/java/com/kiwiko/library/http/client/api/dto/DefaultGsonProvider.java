package com.kiwiko.library.http.client.api.dto;

import com.google.gson.Gson;

class DefaultGsonProvider {
    private static final Gson GSON = new Gson();

    protected Gson getGson() {
        return GSON;
    }
}
