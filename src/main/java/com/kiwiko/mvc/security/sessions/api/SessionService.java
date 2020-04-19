package com.kiwiko.mvc.security.sessions.api;

import com.kiwiko.mvc.security.sessions.data.Session;

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

    void invalidateSession(long sessionId);
}
