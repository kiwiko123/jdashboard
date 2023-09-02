package com.kiwiko.jdashboard.users.client.api.interfaces;

import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.users.client.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.CreateUserInput;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

public interface UserClient {

    GetUserByIdResponse getById(long userId);

    GetUsersByQueryResponse getByQuery(GetUsersQuery query);

    ClientResponse<CreateUserOutput> create(CreateUserInput input);

    User fromLegacyUser(com.kiwiko.jdashboard.users.service.api.dto.User user);
}
