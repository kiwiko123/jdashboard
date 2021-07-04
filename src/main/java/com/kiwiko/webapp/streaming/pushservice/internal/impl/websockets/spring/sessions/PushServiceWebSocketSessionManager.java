package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.sessions;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.streaming.pushservice.api.dto.ClientPushRequest;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class PushServiceWebSocketSessionManager {

    @Inject private Logger logger;

    private Map<String, WebSocketSession> sessionsById;
    private BiMap<Long, String> userSessionIds;

    public PushServiceWebSocketSessionManager() {
        sessionsById = new HashMap<>();
        userSessionIds = HashBiMap.create();
    }

    public void storeSession(WebSocketSession session, ClientPushRequest request) {
        String sessionId = session.getId();
        Long userId = request.getUserId();

        if (userSessionIds.containsKey(userId)) {
            logger.info(String.format("User ID %d already has an active push service session", userId));
            return;
        }
        if (sessionsById.containsKey(sessionId)) {
            logger.error(String.format("Session with ID %s already exists", sessionId));
            return;
        }

        logger.debug(String.format("Starting new push service session %s for user %d", sessionId, userId));
        userSessionIds.put(userId, sessionId);
        sessionsById.put(sessionId, session);
    }

    public Optional<WebSocketSession> getSessionById(String sessionId) {
        return Optional.ofNullable(sessionsById.get(sessionId));
    }

    public Optional<WebSocketSession> getSessionForUser(long userId) {
        return Optional.ofNullable(userSessionIds.get(userId))
                .map(sessionsById::get);
    }

    public void endSession(String sessionId) {
        Long userId = userSessionIds.inverse().get(sessionId);
        endSessionForUser(userId);
    }

    public void endSessionForUser(long userId) {
        String sessionId = userSessionIds.get(userId);
        if (sessionId == null) {
            return;
        }

        logger.debug(String.format("Ending push service session for user ID %d", userId));
        Optional.ofNullable(sessionsById.get(sessionId))
                .ifPresent(this::closeSession);

        sessionsById.remove(sessionId);
        userSessionIds.remove(userId);
    }

    private void closeSession(WebSocketSession session) {
        if (!session.isOpen()) {
            return;
        }

        logger.debug(String.format("Closing push service session %s", session.getId()));

        try {
            session.close(CloseStatus.NORMAL);
        } catch (IOException e) {
            logger.error("Failed to close push session", e);
        }
    }
}
