package com.kiwiko.webapp.mvc.security.sessions.api;

import com.kiwiko.webapp.mvc.security.sessions.data.Session;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;

public interface SessionService {

    Session createSessionForUser(long userId);

    Session createSessionCookieForUser(long userId, HttpServletResponse httpServletResponse);

    Optional<Session> getByUser(long userId);

    Optional<Session> getByToken(String token);

    Collection<Session> getByTokens(Collection<String> tokens);

    Session saveSession(Session session);

    Optional<Session> endSessionForUser(long userId);

    void invalidateSession(long sessionId);
}
