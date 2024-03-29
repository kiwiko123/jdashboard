package com.kiwiko.jdashboard.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.services.users.api.dto.User;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;

public class GetUserByIdApiRequest extends DefaultApiRequest {

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
    public boolean isInternalServiceRequest() {
        return false;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return User.class;
    }
}
