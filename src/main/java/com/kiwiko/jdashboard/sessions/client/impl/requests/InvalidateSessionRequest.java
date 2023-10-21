package com.kiwiko.jdashboard.sessions.client.impl.requests;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.InvalidateSessionInput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.InvalidateSessionOutput;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

public class InvalidateSessionRequest extends JdashboardApiRequest {

    private final InvalidateSessionInput input;

    public InvalidateSessionRequest(InvalidateSessionInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(
                new UriBuilder()
                    .setPath(String.format("/sessions/service-api/%d/invalidate", input.getSessionId())));
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "session-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return InvalidateSessionOutput.class;
    }
}