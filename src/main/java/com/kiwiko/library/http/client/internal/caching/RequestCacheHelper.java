package com.kiwiko.library.http.client.internal.caching;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.caching.impl.InMemoryObjectCache;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;

import java.util.Optional;

public class RequestCacheHelper {
    private static final String CACHE_KEY_PREFIX = String.format("%s.key", RequestCacheHelper.class.getName());

    private final ObjectCache cache;

    public RequestCacheHelper() {
        cache = new InMemoryObjectCache();
    }

    public <T> Optional<HttpClientResponse<T>> getCachedResponse(HttpClientRequest request) {
        String key = makeRequestCacheKey(request);
        return cache.get(key);
    }

    public <T> void cacheResponse(HttpClientRequest request, HttpClientResponse<T> response) {
        if (request.getCachePolicy().getDuration() == null) {
            return;
        }

        String key = makeRequestCacheKey(request);
        if (cache.get(key).isEmpty()) {
            cache.cache(key, response, request.getCachePolicy().getDuration());
        }
    }

    private String makeRequestCacheKey(HttpClientRequest request) {
        String requestId = makeRequestId(request);
        return String.format("%s.%s", CACHE_KEY_PREFIX, requestId);
    }

    private String makeRequestId(HttpClientRequest request) {
        return request.toString();
    }
}
