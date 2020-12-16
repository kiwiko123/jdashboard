package com.kiwiko.webapp.push.internal.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

public class PushServiceUserSessionData {

    private final long userId;
    private final BiMap<String, WebSocketSession> serviceSessionMapping;

    public PushServiceUserSessionData(long userId) {
        this.userId = userId;
        this.serviceSessionMapping = HashBiMap.create();
    }

    public long getUserId() {
        return userId;
    }

    public Optional<WebSocketSession> getSessionByServiceId(String serviceId) {
        return Optional.ofNullable(serviceSessionMapping.get(serviceId));
    }

    public void startSession(String serviceId, WebSocketSession session) {
        serviceSessionMapping.put(serviceId, session);
    }

    public void endSession(WebSocketSession session) {
        serviceSessionMapping.inverse().remove(session);
    }

    public boolean hasSession(WebSocketSession session) {
        return serviceSessionMapping.containsValue(session);
    }

    public void purge() {
        new HashSet<>(serviceSessionMapping.values()).forEach(this::endSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, serviceSessionMapping);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PushServiceUserSessionData)) {
            return false;
        }

        PushServiceUserSessionData other = (PushServiceUserSessionData) obj;
        return Objects.equals(userId, other.userId)
                && Objects.equals(serviceSessionMapping, other.serviceSessionMapping);
    }
}
