package com.kiwiko.jdashboard.servicerequestkeys.service.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.impl.GsonRequestBodyDeserializationStrategy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.adapters.InstantIso8061Adapter;

import java.time.Instant;

public class CreateServiceRequestKeyRequestBodyDeserializer extends GsonRequestBodyDeserializationStrategy {
    @Override
    protected Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantIso8061Adapter())
                .create();
    }
}
