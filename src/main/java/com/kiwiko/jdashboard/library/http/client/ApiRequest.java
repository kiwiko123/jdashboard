package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Set;

public interface ApiRequest {

    /**
     * Required.
     *
     * @return the request method for this request
     */
    RequestMethod getRequestMethod();

    /**
     * Required.
     *
     * @return the URL for this request
     * @see RequestUrl
     */
    RequestUrl getRequestUrl();

    /**
     * Optional.
     *
     * Return the request body to be sent with this request. This will get serialized by {@link #getRequestBodySerializer()}.
     * This only applies to POST and PUT requests.
     *
     * @return the request body
     */
    @Nullable
    Object getRequestBody();

    /**
     * Optional.
     *
     * Return the duration after which the request will time out if exceeded.
     * If not provided, a generous default value may be used.
     *
     * @return the request timeout duration
     */
    @Nullable
    Duration getRequestTimeout();

    /**
     * Required.
     *
     * Only applies if a 3xx HTTP response code is received by the server.
     *
     * @return the redirection policy for this request
     */
    HttpClient.Redirect getRedirectionPolicy();

    /**
     * Required.
     *
     * @return the request headers to be sent with this request
     */
    Set<RequestHeader> getRequestHeaders();

    /**
     * Required.
     *
     * The cache strategy is a signal, but not a guarantee, that the request will be cached as dictated by the strategy.
     * Certain circumstances may prematurely evict the request from the cache.
     *
     * @return the caching strategy for this request
     */
    RequestCacheStrategy getCacheStrategy();

    /**
     * Optional.
     *
     * A string that identifies the caller issuing the request to the recipient.
     * It should not be assumed that this identifier is treated as a secure secret.
     *
     * @return a string that identifies the caller issuing the request
     */
    @Nullable
    String getClientIdentifier();

    /**
     * Required for POST and PUT requests.
     *
     * @return the request body serializer
     */
    PayloadSerializer getRequestBodySerializer();

    /**
     * Required.
     *
     * @return an error handler that parses the HTTP response for errors
     */
    RequestErrorHandler getRequestErrorHandler();

    /**
     * Required.
     *
     * @return the response payload deserializer
     */
    PayloadDeserializer getResponsePayloadDeserializer();


    /**
     * Designate the expected class type for the response.
     * The response payload will be deserialized into this type using {@link #getResponsePayloadDeserializer()}.
     *
     * If {@code null} is returned, the response payload may be ignored.
     *
     * @return the response's class type
     */
    @Nullable
    Class<?> getResponseType();
}