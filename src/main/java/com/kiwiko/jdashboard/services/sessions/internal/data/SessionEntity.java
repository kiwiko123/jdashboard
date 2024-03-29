package com.kiwiko.jdashboard.services.sessions.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "sessions")
public class SessionEntity implements DataEntity, SoftDeletable {

    private Long id;
    private String token;
    private @Nullable Long userId;
    private Instant startTime;
    private @Nullable Instant endTime;
    private Instant createdDate;
    private Instant lastUpdatedDate;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "user_id", nullable = true)
    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Column(name = "start_time", nullable = false)
    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", nullable = true)
    @javax.annotation.Nullable
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(@javax.annotation.Nullable Instant endTime) {
        this.endTime = endTime;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_updated_date", nullable = false)
    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

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
}
