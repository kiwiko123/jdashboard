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
    private @Nullable User user;

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

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public void setUser(@Nullable User user) {
        this.user = user;
    }
}
