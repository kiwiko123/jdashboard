package com.kiwiko.library.caching.impl;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.caching.data.CacheValue;

import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryObjectCache implements ObjectCache {
    private static final Map<String, CacheValue<?>> IN_MEMORY_CACHE = new ConcurrentHashMap<>();

    @Override
    public <T> Optional<T> get(String key) {
        @SuppressWarnings("unchecked")
        CacheValue<T> value = (CacheValue<T>) IN_MEMORY_CACHE.get(key);
        if (value == null) {
            return Optional.empty();
        }

        boolean isExpired = value.getExpirationTime()
                .map(Instant.now()::isAfter)
                .orElse(false);

        if (isExpired) {
            invalidate(key);
            return Optional.empty();
        }

        return Optional.ofNullable(value.getValue());
    }

    @Override
    public <T> T cache(String key, T value) {
        return cache(key, value, null);
    }

    @Override
    public <T> T cache(String key, T value, TemporalAmount duration) {
        CacheValue<T> cacheValue = new CacheValue<>(value, duration);
        IN_MEMORY_CACHE.put(key, cacheValue);
        return value;
    }

    @Override
    public void invalidate(String key) {
        IN_MEMORY_CACHE.remove(key);
    }
}
