package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public abstract class JdashboardApiRequest implements ApiRequest {

    @Nullable
    @Override
    public Object getRequestBody() {
        return null;
    }

    @Nullable
    @Override
    public Duration getRequestTimeout() {
        // Generous default timeout.
        return Duration.ofSeconds(30);
    }

    @Override
    public HttpClient.Redirect getRedirectionPolicy() {
        return HttpClient.Redirect.NORMAL;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean isInternalServiceRequest() {
        return false;
    }

    @Override
    public PayloadSerializer getRequestBodySerializer() {
        return new DefaultGsonPayloadSerializer();
    }

    @Override
    public PayloadDeserializer getResponsePayloadDeserializer() {
        return new DefaultGsonPayloadDeserializer();
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "requestBody=" + getRequestBody() +
                ", requestTimeout=" + getRequestTimeout() +
                ", redirectionPolicy=" + getRedirectionPolicy() +
                ", requestHeaders=" + getRequestHeaders() +
                ", internalServiceRequest=" + isInternalServiceRequest() +
                ", requestBodySerializer=" + getRequestBodySerializer() +
                ", responsePayloadDeserializer=" + getResponsePayloadDeserializer() +
                ", responseType=" + getResponseType() +
                '}';
    }
}
