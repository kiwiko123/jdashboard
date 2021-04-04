package com.kiwiko.library.http.client.dto;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;

public class RequestBuilder {
    private final HttpClientRequest request;

    public RequestBuilder() {
        request = new HttpClientRequest();
    }

    public RequestBuilder setUrl(String url) {
        request.setUrl(url);
        return this;
    }

    public RequestBuilder withHeader(RequestHeader requestHeader) {
        request.getRequestHeaders().add(requestHeader);
        return this;
    }

    public RequestBuilder setTimeout(@Nullable Duration timeout) {
        request.setTimeout(timeout);
        return this;
    }

    public RequestBuilder setRedirectPolicy(HttpClient.Redirect redirectPolicy) {
        request.setRedirectPolicy(redirectPolicy);
        return this;
    }

    public HttpClientRequest build() {
        return request;
    }
}
