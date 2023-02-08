package com.kiwiko.jdashboard.tools.apiclient;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.HttpStatusValidationRequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.RequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collections;
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
    public String getClientIdentifier() {
        return null;
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
