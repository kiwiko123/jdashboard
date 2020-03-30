package com.kiwiko.users.internal;

import com.kiwiko.persistence.properties.api.FieldPropertyMapper;
import com.kiwiko.users.data.User;
import com.kiwiko.users.internal.dataAccess.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityPropertyMapper extends FieldPropertyMapper<UserEntity, User> {

    @Override
    protected Class<User> getTargetType() {
        return User.class;
    }
}
