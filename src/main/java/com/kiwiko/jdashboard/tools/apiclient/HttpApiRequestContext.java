package com.kiwiko.jdashboard.tools.apiclient;

import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.HttpStatusValidationRequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.RequestErrorHandler;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.RequestPlugins;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;

@Data
public abstract class HttpApiRequestContext<R extends HttpApiRequest> {
    private static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final RequestErrorHandler DEFAULT_REQUEST_ERROR_HANDLER = new HttpStatusValidationRequestErrorHandler();
    private static final PayloadSerializer DEFAULT_PAYLOAD_SERIALIZER = new DefaultGsonPayloadSerializer();
    private static final PayloadDeserializer DEFAULT_PAYLOAD_DESERIALIZER = new DefaultGsonPayloadDeserializer();

    private final @Nonnull R request;
    private @Nullable Duration requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    private @Nonnull HttpClient.Redirect redirectPolicy = HttpClient.Redirect.NORMAL;
    private @Nullable RequestCacheStrategy requestCacheStrategy;
    private @Nullable String clientIdentifier;
    private @Nonnull RequestErrorHandler requestErrorHandler = DEFAULT_REQUEST_ERROR_HANDLER;
    private @Nonnull PayloadSerializer requestBodySerializer = DEFAULT_PAYLOAD_SERIALIZER;
    private @Nonnull PayloadDeserializer responseBodyDeserializer = DEFAULT_PAYLOAD_DESERIALIZER;
    private @Nullable Class<?> responseType;

    private @Nullable RequestPlugins<PreRequestPlugin> preRequestPlugins;
    private @Nullable RequestPlugins<PostRequestPlugin> postRequestPlugins;
}
