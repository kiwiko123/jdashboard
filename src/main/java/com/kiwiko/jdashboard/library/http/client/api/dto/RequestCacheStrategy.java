package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;

import java.time.Duration;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public abstract class RequestCacheStrategy {

    private static final Set<RequestMethod> DEFAULT_SUPPORTED_METHODS = EnumSet.of(RequestMethod.GET);

    /**
     * Specify the duration for which the request should be cached.
     * The duration starts at the first time the request is made while not cached.
     *
     * To disable caching, return {@link Optional#empty()}.
     *
     * @return the duration for which the request should be cached
     */
    public abstract Optional<Duration> getCacheDuration();

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
