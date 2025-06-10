package com.kiwiko.jdashboard.http.client;

import com.kiwiko.jdashboard.http.client.serialize.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.http.client.serialize.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.http.client.serialize.PayloadDeserializer;
import com.kiwiko.jdashboard.http.client.serialize.PayloadSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleHttpRequestSettings<T extends HttpRequest> implements HttpRequestSettings<T> {
    private static final PayloadSerializer DEFAULT_PAYLOAD_SERIALIZER = new DefaultGsonPayloadSerializer();
    private static final PayloadDeserializer DEFAULT_PAYLOAD_DESERIALIZER = new DefaultGsonPayloadDeserializer();

    @Nonnull T request;
    @Nullable Duration requestTimeout;

    @Builder.Default
    @Nonnull HttpClient.Redirect redirectPolicy = HttpClient.Redirect.NORMAL;

    @Builder.Default
    @Nullable PayloadSerializer requestBodySerializer = DEFAULT_PAYLOAD_SERIALIZER;

    @Builder.Default
    @Nullable PayloadDeserializer responseBodyDeserializer = DEFAULT_PAYLOAD_DESERIALIZER;
}
