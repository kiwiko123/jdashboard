package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.library.http.client.api.constants.RequestMethod;
import com.kiwiko.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.library.http.client.api.dto.RequestUrl;

import javax.annotation.Nullable;

public class RandomApiRequest extends DefaultApiRequest {

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromString("https://api.publicapis.org/random");
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return String.class;
    }
}
