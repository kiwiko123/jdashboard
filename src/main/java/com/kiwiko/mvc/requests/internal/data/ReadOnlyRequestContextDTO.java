package com.kiwiko.mvc.requests.internal.data;

import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.data.RequestContextDTO;
import com.kiwiko.persistence.identification.TypeIdentifiable;
import com.kiwiko.users.data.User;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class ReadOnlyRequestContextDTO extends TypeIdentifiable<Long> implements RequestContext {

    private final Long id;
    private final String uri;
    private final Instant startTime;
    private final @Nullable User user;

    public ReadOnlyRequestContextDTO(RequestContextDTO requestContext) {
        id = requestContext.getId();
        uri = requestContext.getUri();
        startTime = requestContext.getStartTime();
        user = requestContext.getUser().orElse(null);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public Instant getStartTime() {
        return startTime;
    }

    @Override
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}
