package com.kiwiko.mvc.requests.data;

import com.kiwiko.persistence.identification.Identifiable;
import com.kiwiko.users.data.User;

import java.time.Instant;
import java.util.Optional;

public interface RequestContext extends Identifiable<Long> {

    String getUri();

    Instant getStartTime();

    Optional<User> getUser();
}
