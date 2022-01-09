package com.kiwiko.library.http.client.api.constants;

import com.kiwiko.library.http.client.api.dto.RequestHeader;

public final class RequestHeaders {
    public static final RequestHeader CONTENT_TYPE_JSON = new RequestHeader("Content-Type", "application/json; charset=UTF-8");

    private RequestHeaders() {}
}
