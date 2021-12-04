package com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess;

import com.kiwiko.library.persistence.dataAccess.api.AuditableDataEntity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "request_contexts")
public class RequestContextEntity extends AuditableDataEntity {

    private Long id;
    private String uri;
    private Instant startTime;
    private @Nullable Instant endTime;
    private @Nullable Long userId;
    private Instant createdDate;
    private Instant lastUpdatedDate;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_context_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "created_date", nullable = false)
    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_updated_date", nullable = false)
    @Override
    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @Override
    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Column(name = "is_removed", nullable = false)
    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    @Column(name = "uri", nullable = false)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Column(name = "start_time", nullable = false)
    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", nullable = true)
    @Nullable
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Instant endTime) {
        this.endTime = endTime;
    }

    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
