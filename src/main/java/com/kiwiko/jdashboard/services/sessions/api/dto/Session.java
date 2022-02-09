package com.kiwiko.jdashboard.services.sessions.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Optional;

public class Session extends SoftDeletableDataEntityDTO {

    private String token;
    private Instant startTime;
    private @Nullable Instant endTime;
    private @Nullable Long userId;
    private Instant createdDate;
    private Instant lastUpdatedDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Optional<Instant> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    public void setEndTime(@Nullable Instant endTime) {
        this.endTime = endTime;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
