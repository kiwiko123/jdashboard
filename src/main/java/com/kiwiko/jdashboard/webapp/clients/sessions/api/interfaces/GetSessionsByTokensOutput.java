package com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;

import java.util.Collections;
import java.util.Set;

public class GetSessionsByTokensOutput {
    private Set<Session> sessions = Collections.emptySet();

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }
}
