package com.kiwiko.library.http.client.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ResponseMetadata {
    public static Builder newBuilder() {
        return new Builder();
    }

    private final String url;
    private final Set<RequestHeader> headers;

    public String getUrl() {
        return url;
    }

    public Set<RequestHeader> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "ResponseMetadata{" +
                "url='" + url + '\'' +
                '}';
    }

    private ResponseMetadata(String url, Set<RequestHeader> headers) {
        this.url = url;
        this.headers = headers;
    }

    public static final class Builder {
        private String url;
        private Set<RequestHeader> headers = new HashSet<>();

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHeaders(Collection<RequestHeader> headers) {
            this.headers = new HashSet<>(headers);
            return this;
        }

        public ResponseMetadata build() {
            return new ResponseMetadata(url, headers);
        }
    }
}
