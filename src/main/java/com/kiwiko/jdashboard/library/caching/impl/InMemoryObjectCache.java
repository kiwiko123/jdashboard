package com.kiwiko.jdashboard.library.caching.impl;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.caching.impl.data.CacheValue;
import com.kiwiko.jdashboard.library.lang.random.RandomUtil;

import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryObjectCache implements ObjectCache {
    private final Map<String, CacheValue<?>> IN_MEMORY_CACHE;
    private static final int MAX_ENTRIES_TO_EXAMINE_FOR_CLEANING = 50;
    private static final int CHANCE_TO_CLEAN = 10;

    private final RandomUtil randomUtil;

    public InMemoryObjectCache() {
        IN_MEMORY_CACHE = new ConcurrentHashMap<>();
        randomUtil = new RandomUtil();
    }

    @Override
    public <T> Optional<T> get(String key) {
        if (randomUtil.chance(CHANCE_TO_CLEAN)) {
            clean();
        }

        CacheValue<?> value = IN_MEMORY_CACHE.get(key);
        if (value == null) {
            return Optional.empty();
        }

        if (isExpired(value)) {
            invalidate(key);
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
    public <T> T cache(String key, T value, TemporalAmount duration) {
        CacheValue<T> cacheValue = new CacheValue<>(value, duration);
        IN_MEMORY_CACHE.put(key, cacheValue);
        return value;
    }

    @Override
    public void invalidate(String key) {
        IN_MEMORY_CACHE.remove(key);
    }

    private boolean isExpired(CacheValue<?> value) {
        return value.getExpirationTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }

    private void clean() {
        List<String> expiredKeys = new LinkedList<>();
        int examinedCount = 0;

        for (String key : IN_MEMORY_CACHE.keySet()) {
            if (examinedCount++ >= MAX_ENTRIES_TO_EXAMINE_FOR_CLEANING) {
                break;
            }

            CacheValue<?> value = IN_MEMORY_CACHE.get(key);
            if (isExpired(value)) {
                expiredKeys.add(key);
            }
        }

        expiredKeys.forEach(this::invalidate);
    }
}
