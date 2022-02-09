package com.kiwiko.jdashboard.services.sessions.api.interfaces;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;

import java.time.Instant;

public class SessionHelper {

    public boolean isExpired(Session session) {
        return session.getEndTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }
}
