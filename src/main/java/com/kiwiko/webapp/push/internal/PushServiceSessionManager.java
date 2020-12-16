package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.internal.data.PushServiceUserSessionData;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class PushServiceSessionManager {

    @Inject private LogService logService;

    private Map<Long, PushServiceUserSessionData> userSessionMapping;

    public PushServiceSessionManager() {
        userSessionMapping = new HashMap<>();
    }

    public void startSession(long userId, String serviceId, WebSocketSession session) {
        logService.debug(
                String.format(
                    "Starting new push service %s session %s for user %d",
                    serviceId,
                    session.getId(),
                    userId));

        PushServiceUserSessionData sessionData = userSessionMapping.computeIfAbsent(userId, PushServiceUserSessionData::new);
        sessionData.startSession(serviceId, session);
    }

    public boolean hasSession(Long userId, String serviceId) {
        return Optional.ofNullable(userSessionMapping.get(userId))
                .flatMap(sessionData -> sessionData.getSessionByServiceId(serviceId))
                .isPresent();
    }

    public Optional<WebSocketSession> getSessionForUser(long userId, String serviceId) {
        return Optional.ofNullable(userSessionMapping.get(userId))
                .flatMap(sessionData -> sessionData.getSessionByServiceId(serviceId));
    }

    public void sync(long userId, String serviceId, WebSocketSession session) {
        boolean sessionMatches = getSessionForUser(userId, serviceId)
                .map(WebSocketSession::getId)
                .map(id -> Objects.equals(id, session.getId()))
                .orElse(false);
        if (sessionMatches) {
            return;
        }

        endSession(userId, serviceId);
        startSession(userId, serviceId, session);
    }

    public void endSession(WebSocketSession session) {
        closeSession(session);
        // TODO optimize this; perhaps by storing an auxiliary mapping of session to user id?
        PushServiceUserSessionData sessionData = userSessionMapping.values().stream()
                .filter(data -> data.hasSession(session))
                .findFirst()
                .orElse(null);
        if (sessionData == null) {
            return;
        }

        sessionData.endSession(session);
        logService.debug(String.format("Ended session %s for user %d / (session)", session.getId(), sessionData.getUserId()));
    }

    public void endSession(long userId, String serviceId) {
        PushServiceUserSessionData userSessionData = Optional.ofNullable(userSessionMapping.get(userId))
                .orElse(null);
        if (userSessionData == null) {
            return;
        }

        WebSocketSession session = userSessionData.getSessionByServiceId(serviceId)
                .orElse(null);
        if (session == null) {
            return;
        }

        closeSession(session);
        userSessionData.endSession(session);
        logService.debug(String.format("Ended %s session %s for user %d / (userId, serviceId)", serviceId, session.getId(), userId));
    }

    public void purge() {
        new HashSet<>(userSessionMapping.keySet()).stream()
                .map(userSessionMapping::get)
                .forEach(PushServiceUserSessionData::purge);
    }

    private void closeSession(WebSocketSession session) {
        if (!session.isOpen()) {
            return;
        }

        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (IOException e) {
            logService.error("Failed to close push session", e);
        }
    }
}
