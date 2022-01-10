package com.kiwiko.jdashboard.webapp.framework.security.sessions.data;

import com.kiwiko.library.persistence.dataAccess.data.AuditableDataEntityDTO;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Optional;

public class Session extends AuditableDataEntityDTO {

    private String token;
    private Instant startTime;
    private @Nullable Instant endTime;
    private @Nullable Long userId;

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
}
