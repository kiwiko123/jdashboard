package com.kiwiko.jdashboard.services.users.api.interfaces;

import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.services.users.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.services.users.api.dto.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(long id);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    GetUsersByQueryResponse getByQuery(GetUsersQuery query);

    @Deprecated
    User create(CreateUserParameters parameters);

    User create(User user);

    User merge(User user);

    com.kiwiko.jdashboard.clients.users.api.dto.User toUser(User user);
}
