package com.kiwiko.jdashboard.webapp.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.InvalidateSessionInput;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.InvalidateSessionOutput;

import javax.annotation.Nullable;

public class InvalidateSessionRequest extends DefaultApiRequest {

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
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return InvalidateSessionOutput.class;
    }
}
