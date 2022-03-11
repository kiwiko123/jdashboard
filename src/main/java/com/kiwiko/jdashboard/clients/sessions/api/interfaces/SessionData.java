package com.kiwiko.jdashboard.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.clients.sessions.api.dto.Session;

import java.util.Objects;

public class SessionData {
    private Session session;
    private boolean isExpired;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionData that = (SessionData) o;
        return isExpired == that.isExpired && Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, isExpired);
    }
}
