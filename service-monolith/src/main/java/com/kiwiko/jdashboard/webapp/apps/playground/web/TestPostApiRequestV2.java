package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;

public class TestPostApiRequestV2 extends HttpApiRequest {
    private RequestUrl requestUrl;

    public TestPostApiRequestV2() {
        this.requestUrl = RequestUrl.fromString("http://localhost:7002/public-api/test");
    }

    @Nonnull
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Nonnull
    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }
}
