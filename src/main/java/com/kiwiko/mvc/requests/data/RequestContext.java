package com.kiwiko.mvc.requests.data;

import com.kiwiko.persistence.dataAccess.data.AuditableDataEntityDTO;
import com.kiwiko.users.data.User;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class RequestContext extends AuditableDataEntityDTO {

    private String uri;
    private Instant startTime;
    private @Nullable Instant endTime;
    private @Nullable User user;

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

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public void setUser(@Nullable User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), getId().toString(), getStartTime().toString());
    }
}
