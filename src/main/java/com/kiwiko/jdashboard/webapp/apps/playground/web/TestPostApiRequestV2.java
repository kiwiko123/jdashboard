package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestPostApiRequestV2 extends HttpApiRequest {
    private RequestUrl requestUrl;
    private String message;

    public TestPostApiRequestV2(String message) {
        this.requestUrl = RequestUrl.fromPartial(new UriBuilder().setPath("/playground-api/test"));
        this.message = message;
    }

    @Nonnull
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Nonnull
    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return message;
    }
}
