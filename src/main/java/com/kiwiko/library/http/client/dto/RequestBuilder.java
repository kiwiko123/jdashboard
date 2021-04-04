package com.kiwiko.library.http.client.dto;

import com.kiwiko.library.http.client.dto.caching.RequestCachePolicy;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;

public abstract class RequestBuilder<T extends HttpClientRequest> {
    private final T request;

    protected abstract T constructRequest();

    public RequestBuilder() {
        request = constructRequest();
    }

    public RequestBuilder<T> setUrl(String url) {
        request.setUrl(url);
        return this;
    }

    public RequestBuilder<T> withHeader(RequestHeader requestHeader) {
        request.getRequestHeaders().add(requestHeader);
        return this;
    }

    public RequestBuilder<T> setTimeout(@Nullable Duration timeout) {
        request.setTimeout(timeout);
        return this;
    }

    public RequestBuilder<T> setRedirectPolicy(HttpClient.Redirect redirectPolicy) {
        request.setRedirectPolicy(redirectPolicy);
        return this;
    }

    public RequestBuilder<T> setCachePolicy(RequestCachePolicy cachePolicy) {
        request.setCachePolicy(cachePolicy);
        return this;
    }

    public T build() {
        return request;
    }
}
