package com.kiwiko.jdashboard.services.users.api.interfaces;

import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.services.users.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.services.users.api.dto.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getById(long id);

    Set<User> getByIds(Collection<Long> ids);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    GetUsersByQueryResponse getByQuery(GetUsersQuery query);

    User create(CreateUserParameters parameters);

    User merge(User user);
}
