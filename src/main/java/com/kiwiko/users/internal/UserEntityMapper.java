package com.kiwiko.users.internal;

import com.kiwiko.persistence.properties.api.EntityMapper;
import com.kiwiko.users.data.User;
import com.kiwiko.users.internal.dataAccess.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityMapper extends EntityMapper<UserEntity, User> {

    @Override
    protected Class<UserEntity> getEntityType() {
        return UserEntity.class;
    }

    @Override
    protected Class<User> getDTOType() {
        return User.class;
    }
}
