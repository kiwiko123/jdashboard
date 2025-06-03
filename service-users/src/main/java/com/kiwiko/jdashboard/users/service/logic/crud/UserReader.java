package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.users.service.data.UserDataAccessObject;
import com.kiwiko.jdashboard.users.service.dto.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

public class UserReader {
    @Inject private UserEntityMapper userEntityMapper;
    @Inject private UserDataAccessObject userDataAccessObject;

    @Transactional
    public Optional<User> get(long id) {
        return userDataAccessObject.get(id)
                .map(userEntityMapper::toDto);
    }

    @Transactional
    public Optional<User> getByUsername(String username) {
        return userDataAccessObject.getByUsername(username)
                .map(userEntityMapper::toDto);
    }
}
