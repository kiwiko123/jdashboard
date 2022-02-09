package com.kiwiko.jdashboard.webapp.framework.interceptors.internal;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionHelper;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionRequestHelper {

    @Inject private SessionService sessionService;
    @Inject private SessionHelper sessionHelper;
    @Inject private Logger logger;

    public Optional<Session> getSessionFromRequest(HttpServletRequest request) {
        List<Cookie> cookies = Optional.ofNullable(request.getCookies())
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);

        List<String> tokens = cookies.stream()
                .filter(cookie -> Objects.equals(cookie.getName(), SessionProperties.AUTHENTICATION_COOKIE_NAME))
                .map(Cookie::getValue)
                .collect(Collectors.toList());

        if (tokens.isEmpty()) {
            return Optional.empty();
        }

        Set<Session> sessions = sessionService.getByTokens(tokens);
        Set<Session> activeSessions = new HashSet<>();

        for (Session session : sessions) {
            if (sessionHelper.isExpired(session)) {
                sessionService.invalidateSession(session.getId());
            } else {
                activeSessions.add(session);
            }
        }

        if (activeSessions.size() > 1) {
            String userIds = activeSessions.stream()
                    .map(Session::getUserId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            logger.info(String.format("%d active sessions found for user IDs %s", activeSessions.size(), userIds));
        }

        return activeSessions.stream()
                .findFirst();
    }
}
