package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.users.service.data.UserDataAccessObject;
import com.kiwiko.jdashboard.users.service.data.entity.UserEntity;
import com.kiwiko.jdashboard.users.service.dto.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

public class UserWriter {
    @Inject private UserEntityMapper userEntityMapper;
    @Inject private UserDataAccessObject userDataAccessObject;

    @Transactional
    public User create(User user) {
        UserEntity entityToCreate = userEntityMapper.toEntity(user);
        UserEntity createdEntity = userDataAccessObject.insert(entityToCreate);
        return userEntityMapper.toDto(createdEntity);
    }
}
