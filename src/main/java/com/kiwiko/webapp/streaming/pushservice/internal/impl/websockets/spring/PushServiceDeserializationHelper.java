package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.mvc.json.gson.GsonProvider;
import com.kiwiko.webapp.streaming.pushservice.api.dto.ClientPushRequest;

import javax.inject.Inject;

public class PushServiceDeserializationHelper {

    @Inject private GsonProvider gsonProvider;
    @Inject private Logger logger;

    public ClientPushRequest deserializeClientPushRequest(String data) {
        Gson gson = gsonProvider.getDefault();
        try {
            return gson.fromJson(data, ClientPushRequest.class);
        } catch (JsonSyntaxException e) {
            logger.error(String.format("Error deserializing client push request JSON: %s", data), e);
            throw e;
        }
    }
}
