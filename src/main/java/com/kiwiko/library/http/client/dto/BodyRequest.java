package com.kiwiko.library.http.client.dto;

import javax.annotation.Nullable;

class BodyRequest extends HttpClientRequest {
    private @Nullable Object body;

    @Nullable
    public Object getBody() {
        return body;
    }

    public void setBody(@Nullable Object body) {
        this.body = body;
    }
}
