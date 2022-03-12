package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;

import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;

public abstract class RequestCacheStrategy {

    private static final Set<RequestMethod> DEFAULT_SUPPORTED_METHODS = EnumSet.of(RequestMethod.GET);

    /**
     * Control if the request can be cached.
     * Returning true signals that the API client should attempt to cache the request, however it is not guaranteed.
     *
     * @return true if the request should be cached, or false if not
     */
    public boolean shouldCacheRequest() {
        return true;
    }

    /**
     * Specify the duration for which the request should be cached.
     * The duration starts at the first time the request is made while not cached.
     *
     * If {@code null} is returned, caching will be treated as disabled.
     *
     * @return the duration for which the request should be cached
     */
    public Duration getCacheDuration() {
        return null;
    }

    /**
     * @param request the input request object
     * @return the cache key for this request
     */
    public String getCacheKey(ApiRequest request) {
        return request.toString();
    }

    /**
     * Specify the request method types to which caching can apply.
     * Currently, this is strictly limited to GET requests.
     *
     * @return the request method types to which caching can apply
     */
    public final Set<RequestMethod> getSupportedMethods() {
        return DEFAULT_SUPPORTED_METHODS;
    }
}
