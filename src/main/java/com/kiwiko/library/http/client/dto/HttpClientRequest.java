package com.kiwiko.library.http.client.dto;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HttpClientRequest {

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    private String url;
    private Set<RequestHeader> requestHeaders;
    private Duration timeout;
    private HttpClient.Redirect redirectPolicy;

    public HttpClientRequest() {
        requestHeaders = new HashSet<>();
        timeout = Duration.ofSeconds(10);
        redirectPolicy = HttpClient.Redirect.NORMAL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<RequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Collection<RequestHeader> requestHeaders) {
        this.requestHeaders = new HashSet<>(requestHeaders);
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public HttpClient.Redirect getRedirectPolicy() {
        return redirectPolicy;
    }

    public void setRedirectPolicy(HttpClient.Redirect redirectPolicy) {
        this.redirectPolicy = redirectPolicy;
    }
}
