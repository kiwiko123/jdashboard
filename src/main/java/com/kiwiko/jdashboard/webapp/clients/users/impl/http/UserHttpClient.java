package com.kiwiko.jdashboard.webapp.clients.users.impl.http;

import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.clients.users.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests.GetUserByIdApiRequest;
import com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests.GetUsersByQueryApiRequest;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.library.http.client.api.dto.ApiResponse;
import com.kiwiko.library.http.client.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class UserHttpClient implements UserClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserDtoMapper userDtoMapper;
    @Inject private GsonProvider gsonProvider;

    @Override
    public GetUserByIdResponse getById(long userId) {
        GetUserByIdApiRequest request = new GetUserByIdApiRequest(userId);
        ApiResponse<com.kiwiko.jdashboard.webapp.users.data.User> response = null;
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

        ApiResponse<GetUsersByQueryResponse> apiResponse = null;
        try {
            apiResponse = jdashboardApiClient.synchronousCall(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return apiResponse.getPayload();
    }

    @Override
    public User fromLegacyUser(com.kiwiko.jdashboard.webapp.users.data.User user) {
        return userDtoMapper.toTargetType(user);
    }
}
