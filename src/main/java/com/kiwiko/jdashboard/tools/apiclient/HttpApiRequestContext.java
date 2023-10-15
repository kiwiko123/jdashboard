package com.kiwiko.jdashboard.tools.apiclient;

import com.google.common.collect.ImmutableList;
import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.PayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.RequestPlugins;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;

public abstract class HttpApiRequestContext<R extends HttpApiRequest> {
    private static final PayloadSerializer DEFAULT_PAYLOAD_SERIALIZER = new DefaultGsonPayloadSerializer();
    private static final PayloadDeserializer DEFAULT_PAYLOAD_DESERIALIZER = new DefaultGsonPayloadDeserializer();

    private final @Nonnull R request;
    private @Nonnull RequestPlugins<PreRequestPlugin> preRequestPlugins;
    private @Nonnull RequestPlugins<PostRequestPlugin> postRequestPlugins;

    public HttpApiRequestContext(@Nonnull R request) {
        this.request = request;
        preRequestPlugins = RequestPlugins.of();
        postRequestPlugins = RequestPlugins.of();
    }

    /**
     * @return the {@link HttpApiRequest} object associated with this context
     */
    @Nonnull
    public final R getRequest() {
        return request;
    }

    /**
     * (Optional) The maximum duration to wait until the request will terminate due to a timeout. If omitted, or if the
     * specified duration is excessively long, a default timeout may be used instead.
     *
     * @return the maximum duration to wait before the request times out
     */
    @Nullable
    public Duration getRequestTimeout() {
        return null;
    }

    /**
     * (Required)
     *
     * @return the HTTP redirect policy of this request
     */
    @Nonnull
    public HttpClient.Redirect getRedirectPolicy() {
        return HttpClient.Redirect.NORMAL;
    }

    /**
     * (Optional) The cache strategy for this request. If null, the request will not be cached.
     *
     * TODO: Investigate if this can be instead implemented as a {@link PreRequestPlugin}.
     *
     * @return the cache strategy for this request
     */
    @Nullable
    public RequestCacheStrategy getRequestCacheStrategy() {
        return null;
    }

    /**
     * (Optional) This should only be provided if this is request is being made to an internal Jdashboard service.
     *
     * @return the identifier/name of the client issuing this request
     */
    @Nullable
    public String getClientIdentifier() {
        return null;
    }

    /**
     * (Required)
     *
     * @return the request body serializer
     */
    @Nonnull
    public PayloadSerializer getRequestBodySerializer() {
        return DEFAULT_PAYLOAD_SERIALIZER;
    }

    /**
     * (Required)
     *
     * @return the response body deserializer
     */
    @Nonnull
    public PayloadDeserializer getResponseBodyDeserializer() {
        return DEFAULT_PAYLOAD_DESERIALIZER;
    }

    /**
     * (Technically optional, but required in most cases) The Java type of the response.
     * {@link #getResponseBodyDeserializer()} will attempt to deserialize the response body into an object of this type.
     * Only omit this if you are not expecting a response from the server.
     *
     * @return the type of the object into which the response body will be deserialized
     */
    @Nullable
    public abstract Class<?> getResponseType();

    /**
     * Invoke this method to add a pre-request plugin to the request. This can be utilized in a programmatic context,
     * like in a {@link com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin}.
     *
     * @param preRequestPlugin the pre-request plugin to add
     */
    public final void addPreRequestPlugin(@Nonnull PreRequestPlugin preRequestPlugin) {
        List<PreRequestPlugin> newPlugins = new ImmutableList.Builder<PreRequestPlugin>()
                .addAll(preRequestPlugins.getPlugins())
                .add(preRequestPlugin)
                .build();
        this.preRequestPlugins = RequestPlugins.of(newPlugins);
    }

    /**
     * Invoke this method to add a post-request plugin to the request. This can be utilized in a programmatic context,
     * like in a {@link com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin}.
     *
     * @param postRequestPlugin the post-request plugin to add
     */
    public final void addPostRequestPlugin(@Nonnull PostRequestPlugin postRequestPlugin) {
        List<PostRequestPlugin> newPlugins = new ImmutableList.Builder<PostRequestPlugin>()
                .addAll(postRequestPlugins.getPlugins())
                .add(postRequestPlugin)
                .build();
        this.postRequestPlugins = RequestPlugins.of(newPlugins);
    }

    /**
     * If true, always run default Jdashboard API client plugins with this request. Default plugins will always run
     * after any custom plugins specified in this request context.
     *
     * @return true if default plugins should be run, or false otherwise
     * @see com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.DefaultJdashboardApiClientPlugins
     */
    public boolean shouldEnableDefaultRequestPlugins() {
        return true;
    }

    /**
     * @return all custom pre-request plugins specified in this request context
     */
    @Nonnull
    public final RequestPlugins<PreRequestPlugin> getPreRequestPlugins() {
        return preRequestPlugins;
    }

    /**
     * @return all custom post-request plugins specified in this request context
     */
    @Nonnull
    public final RequestPlugins<PostRequestPlugin> getPostRequestPlugins() {
        return postRequestPlugins;
    }
}
