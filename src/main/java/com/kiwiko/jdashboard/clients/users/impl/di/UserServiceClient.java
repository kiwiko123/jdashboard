package com.kiwiko.jdashboard.clients.users.impl.di;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.users.api.UserService;

import javax.inject.Inject;

public class UserServiceClient implements UserClient {

    @Inject private UserService userService;
    @Inject private UserDtoMapper userDtoMapper;

    @Override
    public GetUserByIdResponse getById(long userId) {
        User user = userService.getById(userId)
                .map(userDtoMapper::toTargetType)
                .orElse(null);

        return new GetUserByIdResponse(user);
    }

    @Override
    public GetUsersByQueryResponse getByQuery(GetUsersQuery query) {
        return userService.getByQuery(query);
    }

    @Override
    public User fromLegacyUser(com.kiwiko.jdashboard.webapp.users.data.User user) {
        return userDtoMapper.toTargetType(user);
    }
}
