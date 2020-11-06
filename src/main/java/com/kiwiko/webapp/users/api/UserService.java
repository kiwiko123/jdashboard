package com.kiwiko.webapp.users.api;

import com.kiwiko.webapp.users.data.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getById(long id);

    Set<User> getByIds(Collection<Long> ids);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    User create(User user);

    Optional<User> getWithValidation(String username, String password);
}
