package com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto;

import javax.annotation.Nullable;

public class ClientPushRequest extends PushRequest {
    private @Nullable String sessionId; // Expected to be null on the initial request

    @Nullable
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(@Nullable String sessionId) {
        this.sessionId = sessionId;
    }
}
