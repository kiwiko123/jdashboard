package com.kiwiko.library.http.url;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;

public class UriBuilder {
    private String scheme;
    private String host;
    private int port;
    private String path;
    private @Nullable String query;
    private @Nullable String fragment;

    public URI build() throws URISyntaxException {
        return new URI(scheme, null, host, port, path, query, fragment);
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
    public String getQuery() {
        return query;
    }

    public UriBuilder setQuery(@Nullable String query) {
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
