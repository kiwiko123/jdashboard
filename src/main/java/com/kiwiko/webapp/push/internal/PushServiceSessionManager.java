package com.kiwiko.webapp.push.internal;

import com.kiwiko.webapp.users.data.User;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A helper that maps web socket session objects to users.
 * Two separate maps are maintained, but kept in sync.
 * To push from the server to the client, we need to look up the {@link WebSocketSession} by user ID.
 * To receive pushes from the client, the server must look up an existing user ID by {@link WebSocketSession}.
 */
@Singleton
public class PushServiceSessionManager {

    /**
     * A mapping of {@link WebSocketSession} to recipient {@link User#getId()}.
     */
    private Map<WebSocketSession, Long> sessionUserMapping;
    private Map<Long, WebSocketSession> userSessionMapping;

    public PushServiceSessionManager() {
        sessionUserMapping = new HashMap<>();
        userSessionMapping = new HashMap<>();
    }

    /**
     * Initially, a null value is associated with this session when the client first opens the connection.
     * When the client makes an actual push, this entry is updated via {@link #sync(WebSocketSession, long)}
     * to map the session to the recipient's user ID.
     *
     * @param session
     */
    public void startSession(WebSocketSession session) {
        sessionUserMapping.put(session, null);
    }

    public void sync(WebSocketSession session, long recipientUserId) {
        sessionUserMapping.put(session, recipientUserId);
        userSessionMapping.put(recipientUserId, session);
    }

    public boolean hasSession(WebSocketSession session) {
        return sessionUserMapping.containsKey(session);
    }

    public Optional<Long> getUserIdBySession(WebSocketSession session) {
        return Optional.ofNullable(sessionUserMapping.get(session));
    }

    public Optional<WebSocketSession> getSessionByUserId(long userId) {
        return Optional.ofNullable(userSessionMapping.get(userId));
    }

    public void endSession(WebSocketSession session) {
        if (!sessionUserMapping.containsKey(session)) {
            return;
        }

        Long userId = sessionUserMapping.get(session);
        userSessionMapping.remove(userId);
        sessionUserMapping.remove(session);
    }
}
