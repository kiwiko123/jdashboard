package com.kiwiko.webapp.mvc.interceptors.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.security.sessions.api.SessionHelper;
import com.kiwiko.webapp.mvc.security.sessions.api.SessionService;
import com.kiwiko.webapp.mvc.security.sessions.data.Session;
import com.kiwiko.webapp.mvc.security.sessions.data.SessionProperties;
import com.kiwiko.webapp.users.data.User;

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
    @Inject private LogService logService;

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
                    .map(Session::getUser)
                    .flatMap(Optional::stream)
                    .map(User::getId)
                    .distinct()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            logService.info(String.format("%d active sessions found for user IDs %s", activeSessions.size(), userIds));
        }

        return activeSessions.stream()
                .findFirst();
    }
}
