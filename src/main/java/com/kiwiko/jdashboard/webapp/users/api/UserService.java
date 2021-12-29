package com.kiwiko.jdashboard.webapp.users.api;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.jdashboard.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.webapp.users.data.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getById(long id);

    Set<User> getByIds(Collection<Long> ids);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    Optional<User> getByLoginParameters(UserLoginParameters parameters);

    GetUsersByQueryResponse getByQuery(GetUsersQuery query);

    User create(CreateUserParameters parameters);

    User merge(User user);
}
