package com.kiwiko.jdashboard.webapp.http.client.api.dto;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public abstract class DefaultApiRequest implements ApiRequest {

    @Override
    public boolean isRelativeUrl() {
        return true;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return null;
    }

    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(5);
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
    public <ResponseType> Class<ResponseType> getResponseType() {
        return null;
    }
}
