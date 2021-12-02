package com.kiwiko.jdashboard.webapp.users.internal;

import com.kiwiko.library.persistence.data.properties.api.interfaces.DataEntityFieldMapper;
import com.kiwiko.jdashboard.webapp.users.data.User;
import com.kiwiko.jdashboard.webapp.users.internal.dataAccess.UserEntity;

import javax.inject.Singleton;

@Singleton
public class UserEntityMapper extends DataEntityFieldMapper<UserEntity, User> {
}
