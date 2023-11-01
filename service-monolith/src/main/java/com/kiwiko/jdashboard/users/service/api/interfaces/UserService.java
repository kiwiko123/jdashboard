package com.kiwiko.jdashboard.users.service.api.interfaces;

import com.kiwiko.jdashboard.users.client.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.users.service.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.users.service.api.dto.User;

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

    com.kiwiko.jdashboard.users.client.api.dto.User toUser(User user);
}
