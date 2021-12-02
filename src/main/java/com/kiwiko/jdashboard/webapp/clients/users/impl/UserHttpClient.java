package com.kiwiko.jdashboard.webapp.clients.users.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kiwiko.jdashboard.webapp.clients.users.api.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.impl.requests.GetUsersQueryRequest;
import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.http.client.impl.JdashboardHttpClient;
import com.kiwiko.jdashboard.webapp.framework.json.impl.GsonJsonSerializer;
import com.kiwiko.jdashboard.webapp.users.data.User;

import javax.inject.Inject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHttpClient implements UserClient {

    @Inject private Logger logger;
    @Inject private GsonJsonSerializer gsonJsonSerializer;
    @Inject private JdashboardHttpClient httpClient;

    @Override
    public List<User> getByQuery(GetUsersQuery query) throws InterruptedException {
        Gson gson = gsonJsonSerializer.gson();
        String queryJson = URLEncoder.encode(gson.toJson(query), StandardCharsets.UTF_8);
        GetRequest request = new GetUsersQueryRequest(queryJson);

        HttpClientResponse<JsonElement> response;
        try {
            response = httpClient.syncGet(request, JsonElement.class);
        } catch (ClientException | ServerException e) {
            logger.error("Failed to query for users", e);
            return new ArrayList<>();
        }

        JsonElement jsonPayload = gson.toJsonTree(response.getPayload())
                .getAsJsonObject()
                .get("payload");
        User[] users = gson.fromJson(jsonPayload, User[].class);
        return Arrays.asList(users);
    }
}
