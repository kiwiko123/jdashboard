package com.kiwiko.jdashboard.webapp.framework.interceptors.internal;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.InvalidateSessionInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.InvalidateSessionOutput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.SessionData;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
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

    @Inject private SessionClient sessionClient;
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

        GetSessionsInput getSessionsInput = GetSessionsInput.newBuilder()
                .setTokens(tokens)
                .setIsActive(true)
                .build();
        ClientResponse<GetSessionsOutput> getSessionsResponse;
        try {
            getSessionsResponse = sessionClient.get(getSessionsInput);
        } catch (ApiClientException | InterruptedException e) {
            logger.error(String.format("Error fetching sessions for request %s", request.getRequestURL().toString()), e);
            return Optional.empty();
        }

        if (!getSessionsResponse.getStatus().isSuccessful()) {
            logger.error("Unsuccessful GetSessions client response");
            return Optional.empty();
        }

        if (getSessionsResponse.getPayload() == null) {
            logger.warn("No session payload found");
            return Optional.empty();
        }

        Set<Session> activeSessions = new HashSet<>();
        for (SessionData session : getSessionsResponse.getPayload().getSessions()) {
            if (session.getIsExpired()) {
                invalidateSession(session.getSession().getId());
            } else {
                activeSessions.add(session.getSession());
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

    @Nullable
    private InvalidateSessionOutput invalidateSession(long sessionId) {
        InvalidateSessionInput input = new InvalidateSessionInput(sessionId);

        try {
            return sessionClient.invalidate(input);
        } catch (ApiClientException | InterruptedException e) {
            logger.error(String.format("Error attempting to invalidate session %d", sessionId), e);
        }

        return null;
    }
}
