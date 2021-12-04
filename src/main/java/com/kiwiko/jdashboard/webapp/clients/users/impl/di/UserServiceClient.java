package com.kiwiko.jdashboard.webapp.clients.users.impl.di;

import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.users.api.UserService;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<User> users = userService.getByQuery(query).stream()
                .map(userDtoMapper::toTargetType)
                .collect(Collectors.toSet());

        GetUsersByQueryResponse response = new GetUsersByQueryResponse();
        response.setUsers(users);

        return response;
    }
}
