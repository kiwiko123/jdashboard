package com.kiwiko.jdashboard.clients.sessions.api.interfaces;

import java.util.Set;

public class GetSessionsOutput {
    private Set<SessionData> sessions;

    public Set<SessionData> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionData> sessions) {
        this.sessions = sessions;
    }
}
