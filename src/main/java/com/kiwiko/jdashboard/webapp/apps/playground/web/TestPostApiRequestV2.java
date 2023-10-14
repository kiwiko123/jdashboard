package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.users.client.api.dto.User;

import java.util.Collections;

public class TestPostApiRequestV2 extends HttpApiRequest {

    public TestPostApiRequestV2(String message) {
        setRequestMethod(RequestMethod.POST);
        setRequestUrl(RequestUrl.fromPartial(new UriBuilder().setPath("/playground-api/test")));
        setRequestBody(message);
        setRequestHeaders(Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON));
    }
}
