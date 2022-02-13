package com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;

public class InvalidateSessionOutput {
    private Session session; // The now-invalidated session.

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
