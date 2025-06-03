package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.framework.dataaccess.mapper.AbstractDataEntityMapper;
import com.kiwiko.jdashboard.users.service.data.entity.UserEntity;
import com.kiwiko.jdashboard.users.service.dto.User;

public class UserEntityMapper extends AbstractDataEntityMapper<UserEntity, User> {
    @Override
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setCreatedDate(user.getCreatedDate());
        entity.setIsRemoved(user.isRemoved());

        return entity;
    }

    @Override
    public User toDto(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .createdDate(userEntity.getCreatedDate())
                .isRemoved(userEntity.getIsRemoved())
                .build();
    }
}
