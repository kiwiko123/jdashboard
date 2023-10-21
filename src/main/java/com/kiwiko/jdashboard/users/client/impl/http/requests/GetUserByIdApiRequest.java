package com.kiwiko.jdashboard.users.client.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.users.service.api.dto.User;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

public class GetUserByIdApiRequest extends JdashboardApiRequest {

    private final long userId;

    public GetUserByIdApiRequest(long userId) {
        super();
        this.userId = userId;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath(String.format("/users/api/%d", userId)));
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new GetUserByIdQueryCacheStrategy();
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "user-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return User.class;
    }
}