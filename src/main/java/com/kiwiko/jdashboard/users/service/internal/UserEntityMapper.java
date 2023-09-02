package com.kiwiko.jdashboard.users.service.internal;

import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityFieldMapper;
import com.kiwiko.jdashboard.users.service.api.dto.User;
import com.kiwiko.jdashboard.users.service.internal.data.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityMapper extends DataEntityFieldMapper<UserEntity, User> {
}
