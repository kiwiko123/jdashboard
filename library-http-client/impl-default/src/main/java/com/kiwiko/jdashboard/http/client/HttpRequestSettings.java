package com.kiwiko.jdashboard.http.client;

import com.kiwiko.jdashboard.http.client.serialize.PayloadDeserializer;
import com.kiwiko.jdashboard.http.client.serialize.PayloadSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;

public interface HttpRequestSettings<T extends HttpRequest> {

    @Nonnull
    T getRequest();

    @Nullable
    Duration getRequestTimeout();

    @Nonnull
    HttpClient.Redirect getRedirectPolicy();

    @Nullable
    PayloadSerializer getRequestBodySerializer();

    @Nullable
    PayloadDeserializer getResponseBodyDeserializer();
}
