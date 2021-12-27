package com.kiwiko.jdashboard.webapp.http.client.api.dto;

import com.kiwiko.jdashboard.webapp.http.client.api.constants.RequestMethod;
import com.kiwiko.library.http.url.UriBuilder;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Set;

public interface ApiRequest {

    RequestMethod getRequestMethod();

    UriBuilder getUriBuilder();

    boolean isRelativeUrl();

    boolean encodeUrlQuery();

    @Nullable
    Object getRequestBody();

    @Nullable
    Duration getRequestTimeout();

    HttpClient.Redirect getRedirectPolicy();

    Set<RequestHeader> getRequestHeaders();

    boolean isInternalServiceRequest();

    PayloadSerializer getPayloadSerializer();

    PayloadDeserializer getPayloadDeserializer();

    @Nullable
    <ResponseType> Class<ResponseType> getResponseType();
}
