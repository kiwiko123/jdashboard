package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.HttpStatusValidationRequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public abstract class JdashboardApiRequest implements ApiRequest {
    private static final PayloadSerializer DEFAULT_PAYLOAD_SERIALIZER = new DefaultGsonPayloadSerializer();
    private static final PayloadDeserializer DEFAULT_PAYLOAD_DESERIALIZER = new DefaultGsonPayloadDeserializer();
    private static final RequestErrorHandler DEFAULT_REQUEST_ERROR_HANDLER = new HttpStatusValidationRequestErrorHandler();

    @Nullable
    @Override
    public Object getRequestBody() {
        return null;
    }

    @Nullable
    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(10);
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
        return DEFAULT_PAYLOAD_SERIALIZER;
    }

    @Override
    public RequestErrorHandler getRequestErrorHandler() {
        return DEFAULT_REQUEST_ERROR_HANDLER;
    }

    @Override
    public PayloadDeserializer getResponsePayloadDeserializer() {
        return DEFAULT_PAYLOAD_DESERIALIZER;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return null;
    }
}
