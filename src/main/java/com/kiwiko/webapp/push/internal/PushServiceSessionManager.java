package com.kiwiko.webapp.push.internal;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class PushServiceSessionManager {
    private static final BiMap<Long, WebSocketSession> userSessionMapping = HashBiMap.create();

    @Inject private Logger logger;

    public void startSession(long userId, WebSocketSession session) {
        logger.debug(
                String.format(
                    "Starting new push service session %s for user %d",
                    session.getId(),
                    userId));

        userSessionMapping.put(userId, session);
    }

    public Optional<WebSocketSession> getSessionForUser(long userId) {
        return Optional.ofNullable(userSessionMapping.get(userId));
    }

    public void sync(long userId, WebSocketSession session) {
        boolean sessionMatches = getSessionForUser(userId)
                .map(WebSocketSession::getId)
                .map(id -> Objects.equals(id, session.getId()))
                .orElse(false);
        if (sessionMatches) {
            return;
        }

        endSession(session);
        startSession(userId, session);
    }

    public void endSession(WebSocketSession session) {
        closeSession(session);
        Long userId = userSessionMapping.inverse().get(session);
        userSessionMapping.inverse().remove(session);
        logger.debug(String.format("Ended push service session %s for user %d", session.getId(), userId));
    }

    public void purge() {
        new HashSet<>(userSessionMapping.values()).forEach(this::endSession);
    }

    private void closeSession(WebSocketSession session) {
        if (!session.isOpen()) {
            return;
        }

        try {
            session.close(CloseStatus.NORMAL);
        } catch (IOException e) {
            logger.error("Failed to close push session", e);
        }
    }
}
