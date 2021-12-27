package com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.webapp.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.StatefulApiRequest;
import com.kiwiko.jdashboard.webapp.users.data.User;

import javax.annotation.Nullable;

public class GetUserByIdApiRequest extends StatefulApiRequest {

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
    public String getUrl() {
        return String.format("/users/api/%d", userId);
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public <ResponseType> Class<ResponseType> getResponseType() {
        return (Class<ResponseType>) User.class;
    }
}
