package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
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

    @Nullable
    @Override
    public String getServiceClientIdentifier() {
        return null;
    }

    @Override
    public boolean isInternalServiceRequest() {
        return Objects.equals(getServiceClientIdentifier(), JdashboardServiceClientIdentifiers.DEFAULT);
    }

    @Override
    public PayloadSerializer getRequestBodySerializer() {
        return RequestConstants.DEFAULT_PAYLOAD_SERIALIZER;
    }

    @Override
    public PayloadDeserializer getResponsePayloadDeserializer() {
        return RequestConstants.DEFAULT_PAYLOAD_DESERIALIZER;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "requestUrl=" + getRequestUrl().toUrlString() +
                ", requestMethod=" + getRequestMethod() +
                ", requestBody=" + getRequestBody() +
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
