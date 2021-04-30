package com.kiwiko.library.http.client.dto;

import com.kiwiko.library.http.client.dto.caching.RequestCachePolicy;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HttpClientRequest {
    private String url;
    private Set<RequestHeader> requestHeaders;
    private Duration timeout;
    private HttpClient.Redirect redirectPolicy;
    private RequestCachePolicy cachePolicy;
    private boolean isInternalServiceRequest;

    public HttpClientRequest() {
        requestHeaders = new HashSet<>();
        timeout = Duration.ofSeconds(10);
        redirectPolicy = HttpClient.Redirect.NORMAL;
        cachePolicy = RequestCachePolicy.none();
        isInternalServiceRequest = false;
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

    public void addRequestHeader(RequestHeader requestHeader) {
        requestHeaders.add(requestHeader);
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

    public RequestCachePolicy getCachePolicy() {
        return cachePolicy;
    }

    public void setCachePolicy(RequestCachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public boolean isInternalServiceRequest() {
        return isInternalServiceRequest;
    }

    public void setInternalServiceRequest(boolean internalServiceRequest) {
        isInternalServiceRequest = internalServiceRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HttpClientRequest))
            return false;
        HttpClientRequest that = (HttpClientRequest) o;
        return url.equals(that.url)
                && requestHeaders.equals(that.requestHeaders)
                && timeout.equals(that.timeout)
                && redirectPolicy == that.redirectPolicy
                && cachePolicy.equals(that.cachePolicy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestHeaders, timeout, redirectPolicy, cachePolicy);
    }

    @Override
    public String toString() {
        return String.format(
                "%s(url=\"%s\",requestHeaders=\"%s\",timeout=\"%s\",redirectPolicy=\"%s\",cachePolicy=\"%s\")",
                getClass().getName(),
                url,
                requestHeaders,
                timeout,
                redirectPolicy,
                cachePolicy);
    }
}
