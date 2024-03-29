package com.kiwiko.jdashboard.clients.users.api.interfaces.responses;

import com.kiwiko.jdashboard.clients.users.api.dto.User;

import javax.annotation.Nullable;
import java.util.Optional;

public class GetUserByIdResponse {

    private final @Nullable User user;

    public GetUserByIdResponse(User user) {
        this.user = user;
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}
