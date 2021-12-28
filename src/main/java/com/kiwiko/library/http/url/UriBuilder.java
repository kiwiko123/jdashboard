package com.kiwiko.library.http.url;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class UriBuilder {
    public static UriBuilder fromUri(URI uri) {
        return new UriBuilder()
                .setScheme(uri.getScheme())
                .setHost(uri.getHost())
                .setPort(uri.getPort())
                .setPath(uri.getPath())
                // TODO support query?
                .setFragment(uri.getFragment());
    }

    private String scheme;
    private String host;
    private int port;
    private String path;
    private @Nullable UrlQuery query;
    private @Nullable String fragment;

    public URI build() throws URISyntaxException {
        String queryString = Optional.ofNullable(query)
                .map(UrlQuery::toQuery)
                .orElse(null);
        return new URI(scheme, null, host, port, path, queryString, fragment);
    }

    public String getScheme() {
        return scheme;
    }

    public UriBuilder setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getHost() {
        return host;
    }

    public UriBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public UriBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public UriBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    @Nullable
    public UrlQuery getQuery() {
        return query;
    }

    public UriBuilder setQuery(@Nullable UrlQuery query) {
        this.query = query;
        return this;
    }

    @Nullable
    public String getFragment() {
        return fragment;
    }

    public UriBuilder setFragment(@Nullable String fragment) {
        this.fragment = fragment;
        return this;
    }

    @Override
    public String toString() {
        return "UriBuilder{" +
                "scheme='" + scheme + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", query='" + query + '\'' +
                ", fragment='" + fragment + '\'' +
                '}';
    }
}
