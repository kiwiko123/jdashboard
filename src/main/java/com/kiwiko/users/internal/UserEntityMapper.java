package com.kiwiko.users.internal;

import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.users.data.User;
import com.kiwiko.users.internal.dataAccess.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityMapper extends BidirectionalFieldMapper<UserEntity, User> {

    @Override
    protected Class<UserEntity> getSourceType() {
        return UserEntity.class;
    }

    @Override
    protected Class<User> getTargetType() {
        return User.class;
    }
}
