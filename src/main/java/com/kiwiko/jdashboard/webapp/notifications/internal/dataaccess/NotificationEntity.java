package com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;
import com.kiwiko.jdashboard.webapp.notifications.data.NotificationSource;
import com.kiwiko.jdashboard.webapp.notifications.data.NotificationStatus;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class NotificationEntity extends DataEntity {

    private Long id;
    private NotificationStatus status;
    private NotificationSource source;
    private Long userId;
    private String content;
    private Instant createdDate;
    private @Nullable Instant receivedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status_id", nullable = false)
    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_source_id", nullable = false)
    public NotificationSource getSource() {
        return source;
    }

    public void setSource(NotificationSource source) {
        this.source = source;
    }

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    public NotificationEntity setContent(String content) {
        this.content = content;
        return this;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "received_date")
    @Nullable
    public Instant getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(@Nullable Instant receivedDate) {
        this.receivedDate = receivedDate;
    }
}
