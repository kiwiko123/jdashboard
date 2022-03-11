package com.kiwiko.jdashboard.services.users.internal;

import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityFieldMapper;
import com.kiwiko.jdashboard.services.users.api.dto.User;
import com.kiwiko.jdashboard.services.users.internal.data.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityMapper extends DataEntityFieldMapper<UserEntity, User> {
}
