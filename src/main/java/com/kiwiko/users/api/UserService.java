package com.kiwiko.users.api;

import com.kiwiko.users.data.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(long id);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailAddress(String emailAddress);

    User create(User user);

    Optional<User> getWithValidation(String username, String password);
}
