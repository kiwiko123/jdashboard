package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;
import java.net.URI;

public class RequestUrl {

    /**
     * Construct a RequestUrl from a string representation of a fully valid URI.
     *
     * @param uri a valid URI
     * @return a RequestUrl representing the input URI string
     */
    public static RequestUrl fromString(String uri) {
        return fromUri(URI.create(uri));
    }

    /**
     * Construct a RequestUrl from a fully valid URI.
     *
     * @param uri a valid URI
     * @return a RequestUrl representing the input URI
     */
    public static RequestUrl fromUri(URI uri) {
        RequestUrl requestUrl = new RequestUrl();
        requestUrl.setUri(uri);

        return requestUrl;
    }

    /**
     * Construct a partial RequestUrl from a URI builder.
     * The UriBuilder does not need to be fully valid; it can omit the scheme and host.
     * Example: {@code /test/api?id=123}.
     *
     * @param uriBuilder a UriBuilder that can represent a partially constructed URL
     * @return the RequestUrl representing the input URI builder
     */
    public static RequestUrl fromPartial(UriBuilder uriBuilder) {
        RequestUrl requestUrl = new RequestUrl();
        requestUrl.setUriBuilder(uriBuilder);

        return requestUrl;
    }

    private @Nullable URI uri;
    private @Nullable UriBuilder uriBuilder;

    private RequestUrl() {}

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

    @Override
    public String toString() {
        return "RequestUrl{" +
                "uri=" + uri +
                ", uriBuilder=" + uriBuilder +
                '}';
    }
}
