package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto.ClientPushRequest;

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
