package com.kiwiko.jdashboard.webapp.framework.security.sessions.api;

import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.Session;

import java.time.Instant;

public class SessionHelper {

    public boolean isExpired(Session session) {
        return session.getEndTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }
}
