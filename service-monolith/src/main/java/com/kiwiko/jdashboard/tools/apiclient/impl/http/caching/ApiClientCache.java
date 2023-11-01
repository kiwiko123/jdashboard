package com.kiwiko.jdashboard.tools.apiclient.impl.http.caching;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public class ApiClientCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClientCache.class);
    private static final String CACHE_KEY_PREFIX = "__JDASHBOARD_API_CLIENT_CACHE";

    @Inject private ObjectCache objectCache;

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        boolean processRequestCaching(RequestType request, RequestContextType requestContext, ApiResponse<?> apiResponse) {
        if (!shouldCacheRequest(request.getRequestMethod(), requestContext.getRequestCacheStrategy())) {
            return false;
        }

        String cacheKey = createCacheKey(request, requestContext);
        Duration cacheDuration = requestContext.getRequestCacheStrategy().getCacheDuration();
        if (cacheDuration == null) {
            objectCache.cache(cacheKey, apiResponse);
        } else {
            objectCache.cache(cacheKey, apiResponse, cacheDuration);
        }

        return true;
    }

    @Deprecated
    public boolean processRequestCaching(ApiRequest apiRequest, ApiResponse<?> apiResponse) {
        if (!shouldCacheRequest(apiRequest.getRequestMethod(), apiRequest.getCacheStrategy())) {
            return false;
        }

        String cacheKey = createCacheKey(apiRequest);
        Duration cacheDuration = apiRequest.getCacheStrategy().getCacheDuration();
        if (cacheDuration == null) {
            objectCache.cache(cacheKey, apiResponse);
        } else {
            objectCache.cache(cacheKey, apiResponse, cacheDuration);
        }

        return true;
    }

    @Deprecated
    public <ResponseType> Optional<ApiResponse<ResponseType>> getCachedResponse(ApiRequest apiRequest) {
        if (!shouldCacheRequest(apiRequest.getRequestMethod(), apiRequest.getCacheStrategy())) {
            return Optional.empty();
        }

        String cacheKey = createCacheKey(apiRequest);
        Optional<ApiResponse<ResponseType>> cachedResponse = objectCache.get(cacheKey);
        if (cachedResponse.isPresent()) {
            LOGGER.debug("[JdashboardApiClient] Retrieved cached response for {}", apiRequest.getClass().getSimpleName());
        }

        return cachedResponse;
    }

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        Optional<ApiResponse<ResponseType>> getCachedResponse(RequestType request, RequestContextType requestContext) {
        if (!shouldCacheRequest(request.getRequestMethod(), requestContext.getRequestCacheStrategy())) {
            return Optional.empty();
        }

        String cacheKey = createCacheKey(request, requestContext);
        Optional<ApiResponse<ResponseType>> cachedResponse = objectCache.get(cacheKey);
        if (cachedResponse.isPresent()) {
            LOGGER.debug("[JdashboardApiClient] Retrieved cached response for {}", request.getClass().getSimpleName());
        }

        return cachedResponse;
    }

    private boolean shouldCacheRequest(RequestMethod requestMethod, @Nullable RequestCacheStrategy requestCacheStrategy) {
        if (requestCacheStrategy == null || !requestCacheStrategy.shouldCacheRequest()) {
            return false;
        }

        return requestCacheStrategy.getSupportedMethods().contains(requestMethod);
    }

    private <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        String createCacheKey(RequestType request, RequestContextType requestContext) {
        RequestCacheStrategy cacheStrategy = requestContext.getRequestCacheStrategy();
        Objects.requireNonNull(cacheStrategy);

        String cacheKey = cacheStrategy.getCacheKey(request, requestContext);
        return String.format("%s:%s", CACHE_KEY_PREFIX, cacheKey);
    }

    @Deprecated
    private String createCacheKey(ApiRequest apiRequest) {
        RequestCacheStrategy cacheStrategy = apiRequest.getCacheStrategy();
        String cacheKey = cacheStrategy.getCacheKey(apiRequest);
        return String.format("%s:%s", CACHE_KEY_PREFIX, cacheKey);
    }
}
