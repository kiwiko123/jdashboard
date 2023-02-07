package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.clients.sessions.api.interfaces.CreateSessionInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.CreateSessionOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CreateSessionRequest extends JdashboardApiRequest {

    private final CreateSessionInput input;

    public CreateSessionRequest(CreateSessionInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath("/sessions/service-api/users/create"));
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return input;
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return JdashboardServiceClientIdentifiers.DEFAULT;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreateSessionOutput.class;
    }
}
