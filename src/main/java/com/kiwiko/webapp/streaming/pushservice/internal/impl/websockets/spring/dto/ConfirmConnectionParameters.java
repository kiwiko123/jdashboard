package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.dto;

public class ConfirmConnectionParameters {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
