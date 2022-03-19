package com.kiwiko.jdashboard.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserInput;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CreateUserRequest extends JdashboardApiRequest {

    private final CreateUserInput input;

    public CreateUserRequest(CreateUserInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath("/users/service-api"));
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return input;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreateUserOutput.class;
    }
}
