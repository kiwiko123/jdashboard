package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.RequestHeader;

public final class RequestHeaders {
    public static final RequestHeader CONTENT_TYPE_JSON = new RequestHeader("Content-Type", "application/json; charset=UTF-8");

    private RequestHeaders() {}
}
