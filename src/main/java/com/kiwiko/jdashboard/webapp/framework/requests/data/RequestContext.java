package com.kiwiko.jdashboard.webapp.framework.requests.data;

import com.kiwiko.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class RequestContext extends SoftDeletableDataEntityDTO {

    private String uri;
    private Instant startTime;
    private @Nullable Instant endTime;
    private @Nullable Long userId;
    private Instant createdDate;
    private Instant lastUpdatedDate;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
