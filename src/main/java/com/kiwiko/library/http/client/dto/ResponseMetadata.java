package com.kiwiko.library.http.client.dto;

public class ResponseMetadata {
    public static Builder newBuilder() {
        return new Builder();
    }

    private final String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ResponseMetadata{" +
                "url='" + url + '\'' +
                '}';
    }

    private ResponseMetadata(String url) {
        this.url = url;
    }

    public static final class Builder {
        private String url;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ResponseMetadata build() {
            return new ResponseMetadata(url);
        }
    }
}
