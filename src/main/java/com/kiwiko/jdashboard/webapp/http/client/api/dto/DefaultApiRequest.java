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

    @Override
    public boolean encodeUrlQuery() {
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
    public HttpClient.Redirect getRedirectPolicy() {
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
    public PayloadSerializer getPayloadSerializer() {
        return new DefaultGsonPayloadSerializer();
    }

    @Override
    public PayloadDeserializer getPayloadDeserializer() {
        return new DefaultGsonPayloadDeserializer();
    }

    @Nullable
    @Override
    public <ResponseType> Class<ResponseType> getResponseType() {
        return null;
    }
}
