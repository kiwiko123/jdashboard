package com.kiwiko.jdashboard.library.caching.impl;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.caching.impl.data.CacheValue;

import javax.inject.Singleton;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryObjectCache implements ObjectCache {
    private final Map<String, CacheValue<?>> IN_MEMORY_CACHE;

    public InMemoryObjectCache() {
        IN_MEMORY_CACHE = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Optional<T> get(String key) {
        CacheValue<?> value = IN_MEMORY_CACHE.get(key);
        if (value == null) {
            return Optional.empty();
        }

        if (isExpired(value)) {
            evict(key);
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        T result = (T) value.getValue();
        return Optional.ofNullable(result);
    }

    @Override
    public <T> T cache(String key, T value) {
        return cache(key, value, null);
    }

    @Override
    public <T> T cache(String key, T value, Duration duration) {
        CacheValue<T> cacheValue = new CacheValue<>(value, duration);
        IN_MEMORY_CACHE.put(key, cacheValue);
        return value;
    }

    @Override
    public <T> Optional<T> evict(String key) {
        @SuppressWarnings("unchecked")
        T evictedValue = (T) IN_MEMORY_CACHE.remove(key);
        return Optional.ofNullable(evictedValue);
    }

    private boolean isExpired(CacheValue<?> value) {
        Long expirationTimeMs = value.getExpirationTimeMs();
        return expirationTimeMs != null && System.currentTimeMillis() >= expirationTimeMs;
    }
}
