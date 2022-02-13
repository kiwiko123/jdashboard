package com.kiwiko.jdashboard.services.sessions.api.interfaces;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsOutput;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SessionService {

    Optional<Session> get(long id);

    GetSessionsOutput get(GetSessionsInput input);

    Session createSessionForUser(long userId);

    Session createSessionCookieForUser(long userId, HttpServletResponse httpServletResponse);

    Optional<Session> getByToken(String token);

    Set<Session> getByTokens(Collection<String> tokens);

    Session saveSession(Session session);

    Optional<Session> endSessionForUser(long userId);

    Session invalidateSession(long sessionId);

    boolean isExpired(Session session);
}
