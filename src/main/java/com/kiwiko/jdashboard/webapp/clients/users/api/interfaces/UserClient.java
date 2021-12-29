package com.kiwiko.jdashboard.webapp.clients.users.api.interfaces;

import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;

public interface UserClient {

    GetUserByIdResponse getById(long userId);

    GetUsersByQueryResponse getByQuery(GetUsersQuery query);

    User fromLegacyUser(com.kiwiko.jdashboard.webapp.users.data.User user);
}
