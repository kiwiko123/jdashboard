package com.kiwiko.mvc.security.sessions.api;

import com.kiwiko.mvc.security.sessions.data.Session;

import java.time.Instant;

public class SessionHelper {

    public boolean isExpired(Session session) {
        return session.getEndTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }
}
