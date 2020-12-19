package com.kiwiko.webapp.notifications.data;

import com.kiwiko.library.persistence.identification.TypeIdentifiable;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class Notification extends TypeIdentifiable<Long> {

    private Long id;
    private NotificationStatus status;
    private NotificationSource source;
    private Long userId;
    private String content;
    private Instant createdDate;
    private @Nullable Instant receivedDate;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationSource getSource() {
        return source;
    }

    public void setSource(NotificationSource source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public Notification setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Notification setContent(String content) {
        this.content = content;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Optional<Instant> getReceivedDate() {
        return Optional.ofNullable(receivedDate);
    }

    public void setReceivedDate(@Nullable Instant receivedDate) {
        this.receivedDate = receivedDate;
    }
}
