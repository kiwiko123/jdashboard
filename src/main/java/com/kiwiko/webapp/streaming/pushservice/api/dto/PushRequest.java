package com.kiwiko.webapp.streaming.pushservice.api.dto;

import java.util.Objects;

public class PushRequest {
    private String serviceId;
    private Long userId;
    private Long recipientUserId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public PushRequest withServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PushRequest withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public PushRequest withRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, userId, recipientUserId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PushRequest)) {
            return false;
        }

        PushRequest other = (PushRequest) obj;
        return Objects.equals(serviceId, other.serviceId)
                && Objects.equals(userId, other.userId)
                && Objects.equals(recipientUserId, other.recipientUserId);
    }
}
