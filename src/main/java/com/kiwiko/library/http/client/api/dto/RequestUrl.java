package com.kiwiko.library.http.client.api.dto;

import com.kiwiko.library.http.url.UriBuilder;

import javax.annotation.Nullable;
import java.net.URI;

public class RequestUrl {

    public static RequestUrl fromString(String uri) {
        return fromUri(URI.create(uri));
    }

    public static RequestUrl fromUri(URI uri) {
        RequestUrl requestUrl = new RequestUrl();
        requestUrl.setUri(uri);

        return requestUrl;
    }

    public static RequestUrl fromPartial(UriBuilder uriBuilder) {
        RequestUrl requestUrl = new RequestUrl();
        requestUrl.setUriBuilder(uriBuilder);

        return requestUrl;
    }

    private @Nullable URI uri;
    private @Nullable UriBuilder uriBuilder;

    @Nullable
    public URI getUri() {
        return uri;
    }

    private void setUri(@Nullable URI uri) {
        this.uri = uri;
    }

    @Nullable
    public UriBuilder getUriBuilder() {
        return uriBuilder;
    }

    private void setUriBuilder(@Nullable UriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder;
    }
}
