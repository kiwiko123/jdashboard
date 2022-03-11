package com.kiwiko.jdashboard.clients.users.impl.http;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.clients.users.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.clients.users.impl.http.requests.GetUserByIdApiRequest;
import com.kiwiko.jdashboard.clients.users.impl.http.requests.GetUsersByQueryApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class UserHttpClient implements UserClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserDtoMapper userDtoMapper;
    @Inject private GsonProvider gsonProvider;

    @Override
    public GetUserByIdResponse getById(long userId) {
        GetUserByIdApiRequest request = new GetUserByIdApiRequest(userId);
        ClientResponse<com.kiwiko.jdashboard.services.users.api.dto.User> response = null;
        try {
            response = jdashboardApiClient.synchronousCall(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new GetUserByIdResponse(fromLegacyUser(response.getPayload()));
    }

    @Override
    public GetUsersByQueryResponse getByQuery(GetUsersQuery query) {
        String queryJson = gsonProvider.getDefault().toJson(query);
        GetUsersByQueryApiRequest request = new GetUsersByQueryApiRequest(queryJson);

        ClientResponse<GetUsersByQueryResponse> response = jdashboardApiClient.silencedSynchronousCall(request);
        if (!response.getStatus().isSuccessful()) {
            throw new RuntimeException(response.getStatus().getErrorMessage());
        }

        return response.getPayload();
    }

    @Override
    public User fromLegacyUser(com.kiwiko.jdashboard.services.users.api.dto.User user) {
        return userDtoMapper.toTargetType(user);
    }
}
