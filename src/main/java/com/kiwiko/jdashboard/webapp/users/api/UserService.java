package com.kiwiko.jdashboard.webapp.users.api;

import com.kiwiko.jdashboard.webapp.mvc.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.jdashboard.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.webapp.users.data.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getById(long id);

    Set<User> getByIds(Collection<Long> ids);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    Optional<User> getByLoginParameters(UserLoginParameters parameters);

    List<User> getByQuery(GetUsersQuery query);

    User create(CreateUserParameters parameters);

    User merge(User user);
}