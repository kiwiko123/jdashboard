package com.kiwiko.jdashboard.clients.sessions.api.interfaces;

public class InvalidateSessionInput {
    private final long sessionId;

    public InvalidateSessionInput(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }
}
