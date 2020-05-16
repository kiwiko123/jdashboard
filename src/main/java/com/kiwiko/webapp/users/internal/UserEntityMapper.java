package com.kiwiko.webapp.users.internal;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.users.data.User;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntity;

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
