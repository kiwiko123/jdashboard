package com.kiwiko.webapp.mvc.security.sessions.api;

import com.kiwiko.webapp.mvc.security.sessions.data.Session;

import java.time.Instant;

public class SessionHelper {

    public boolean isExpired(Session session) {
        return session.getEndTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }
}
