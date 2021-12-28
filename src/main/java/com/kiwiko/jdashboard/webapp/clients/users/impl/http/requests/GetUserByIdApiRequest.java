package com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests;

import com.kiwiko.library.http.client.api.constants.RequestMethod;
import com.kiwiko.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.webapp.users.data.User;
import com.kiwiko.library.http.url.UriBuilder;

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
    public UriBuilder getUriBuilder() {
        return new UriBuilder()
                .setPath(String.format("/users/api/%d", userId));
    }

    @Override
    public boolean isInternalServiceRequest() {
        return false;
    }

    @Nullable
    @Override
    public <ResponseType> Class<ResponseType> getResponseType() {
        return (Class<ResponseType>) User.class;
    }
}
