package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;

public class ApiClientCache {

    private static final String CACHE_KEY_PREFIX = "__JDASHBOARD_API_CLIENT_CACHE:";

    @Inject private ObjectCache objectCache;
    @Inject private Logger logger;

    public boolean processRequestCaching(ApiRequest apiRequest, ApiResponse<?> apiResponse) {
        if (!shouldCacheRequest(apiRequest)) {
            return false;
        }

        String cacheKey = createCacheKey(apiRequest);
        Duration cacheDuration = apiRequest.getCacheStrategy().getCacheDuration().get(); // shouldCacheRequest validates that a cache duration exists
        objectCache.cache(cacheKey, apiResponse, cacheDuration);
        return true;
    }

    public <ResponseType> Optional<ApiResponse<ResponseType>> getCachedResponse(ApiRequest apiRequest) {
        if (!shouldCacheRequest(apiRequest)) {
            return Optional.empty();
        }

        String cacheKey = createCacheKey(apiRequest);
        Optional<ApiResponse<ResponseType>> cachedResponse = objectCache.get(cacheKey);
        if (cachedResponse.isPresent()) {
            logger.debug(String.format("Successfully retrieved cached API response for %s", apiRequest));
        }

        return cachedResponse;
    }

    private boolean shouldCacheRequest(ApiRequest apiRequest) {
        RequestCacheStrategy cacheStrategy = apiRequest.getCacheStrategy();
        if (!cacheStrategy.getSupportedMethods().contains(apiRequest.getRequestMethod())) {
            return false;
        }

        Duration cacheDuration = cacheStrategy.getCacheDuration().orElse(null);
        if (cacheDuration == null) {
            return false;
        }

        return true;
    }

    private String createCacheKey(ApiRequest apiRequest) {
        return String.format("%s%s", CACHE_KEY_PREFIX, apiRequest.getRequestUrl());
    }
}
