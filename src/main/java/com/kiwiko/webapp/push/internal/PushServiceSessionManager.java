package com.kiwiko.webapp.push.internal;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kiwiko.library.metrics.api.LogService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class PushServiceSessionManager {

    @Inject private LogService logService;

    private BiMap<Long, WebSocketSession> userSessionMapping;

    public PushServiceSessionManager() {
        userSessionMapping = HashBiMap.create();
    }

    public void startSession(WebSocketSession session, long userId) {
        logService.debug(String.format("Starting new push service session %s for user %d", session.getId(), userId));
        userSessionMapping.put(userId, session);
    }

    public boolean hasSession(Long userId) {
        return userSessionMapping.containsKey(userId);
    }

    public Optional<WebSocketSession> getSessionByUserId(long userId) {
        return Optional.ofNullable(userSessionMapping.get(userId));
    }

    public void sync(long userId, WebSocketSession session) {
        boolean sessionMatches = getSessionByUserId(userId)
                .map(WebSocketSession::getId)
                .map(id -> Objects.equals(id, session.getId()))
                .orElse(false);
        if (sessionMatches) {
            return;
        }

        endSession(session);
        userSessionMapping.put(userId, session);
    }

    public void endSession(WebSocketSession session) {
        Long userId = userSessionMapping.inverse().get(session);
        if (userId == null) {
            return;
        }

        userSessionMapping.remove(userId);
        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (IOException e) {
                logService.error("Failed to close push session", e);
            }
        }

        logService.debug(String.format("Ended session %s for user %d", session.getId(), userId));
    }
}
