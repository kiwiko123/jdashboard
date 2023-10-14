package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

import javax.annotation.Nonnull;

public class TestPostApiRequestV2Context extends HttpApiRequestContext<TestPostApiRequestV2> {

    public TestPostApiRequestV2Context(@Nonnull TestPostApiRequestV2 request) {
        super(request);
        setResponseType(String.class);
    }
}
