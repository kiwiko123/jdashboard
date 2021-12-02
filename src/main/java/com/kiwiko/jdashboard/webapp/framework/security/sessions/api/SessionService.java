package com.kiwiko.jdashboard.webapp.framework.security.sessions.api;

import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.Session;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SessionService {

    Session createSessionForUser(long userId);

    Session createSessionCookieForUser(long userId, HttpServletResponse httpServletResponse);

    Optional<Session> getByUser(long userId);

    Optional<Session> getByToken(String token);

    Set<Session> getByTokens(Collection<String> tokens);

    Session saveSession(Session session);

    Optional<Session> endSessionForUser(long userId);

    void invalidateSession(long sessionId);
}
