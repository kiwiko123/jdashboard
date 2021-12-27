package com.kiwiko.jdashboard.webapp.http.client.api.dto;

import com.kiwiko.jdashboard.webapp.http.client.api.constants.RequestMethod;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class StatefulApiRequest implements ApiRequest {
    private RequestMethod requestMethod;
    private String url;
    private boolean isRelativeUrl;
    private @Nullable Object requestBody;
    private Duration requestTimeout;
    private HttpClient.Redirect redirectPolicy;
    private Set<RequestHeader> requestHeaders;
    private boolean isInternalServiceRequest;
    private PayloadSerializer payloadSerializer;
    private PayloadDeserializer payloadDeserializer;
    private @Nullable Class<?> responseType;

    public StatefulApiRequest() {
        isRelativeUrl = true;
        redirectPolicy = HttpClient.Redirect.NORMAL;
        requestHeaders = new HashSet<>();
        isInternalServiceRequest = false;
        payloadSerializer = new DefaultGsonPayloadSerializer();
        payloadDeserializer = new DefaultGsonPayloadDeserializer();
    }

    @Override
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isRelativeUrl() {
        return isRelativeUrl;
    }

    public void setIsRelativeUrl(boolean isRelativeUrl) {
        this.isRelativeUrl = isRelativeUrl;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(@Nullable Object requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(Duration requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    @Override
    public HttpClient.Redirect getRedirectPolicy() {
        return redirectPolicy;
    }

    public void setRedirectPolicy(HttpClient.Redirect redirectPolicy) {
        this.redirectPolicy = redirectPolicy;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Set<RequestHeader> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    @Override
    public boolean isInternalServiceRequest() {
        return isInternalServiceRequest;
    }

    public void setIsInternalServiceRequest(boolean isInternalServiceRequest) {
        this.isInternalServiceRequest = isInternalServiceRequest;
    }

    @Override
    public PayloadSerializer getPayloadSerializer() {
        return payloadSerializer;
    }

    public void setPayloadSerializer(PayloadSerializer payloadSerializer) {
        this.payloadSerializer = payloadSerializer;
    }

    @Override
    public PayloadDeserializer getPayloadDeserializer() {
        return payloadDeserializer;
    }

    public void setPayloadDeserializer(PayloadDeserializer payloadDeserializer) {
        this.payloadDeserializer = payloadDeserializer;
    }

    @Nullable
    @Override
    public <ResponseType> Class<ResponseType> getResponseType() {
        return (Class<ResponseType>) responseType;
    }

    public void setResponseType(Class<?> responseType) {
        this.responseType = responseType;
    }
}
