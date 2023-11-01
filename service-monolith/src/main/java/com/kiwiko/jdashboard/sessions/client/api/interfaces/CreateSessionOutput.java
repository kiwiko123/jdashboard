package com.kiwiko.jdashboard.sessions.client.api.interfaces;

import com.kiwiko.jdashboard.sessions.client.api.dto.Session;

public class CreateSessionOutput {
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
