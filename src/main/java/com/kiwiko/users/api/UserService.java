package com.kiwiko.users.api;

import com.kiwiko.users.data.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(long id);

    Optional<User> getByEmailAddress(String emailAddress);

    User create(User user);

    boolean isValidUser(String emailAddress, String password);
}
