package com.kiwiko.memory.caching.internal;

import com.kiwiko.memory.caching.data.CacheValue;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCacheService extends UtilityCacheService {

    private static final Map<String, CacheValue> cacheTable = new HashMap<>();

    @Override
    public <T> Optional<T> get(String key) {
        if (!cacheTable.containsKey(key)) {
            return Optional.empty();
        }

        CacheValue<T> value = cacheTable.get(key);
        boolean isExpired = value.getExpirationTime()
                .map(Instant.now()::isAfter)
                .orElse(false);

        if (isExpired) {
            discard(key);
            return Optional.empty();
        }

        return Optional.ofNullable(value.getValue());
    }

    @Override
    public <T> void cache(String key, T value, TemporalAmount duration) {
        CacheValue<T> cacheValue = new CacheValue<>(value, duration);
        cacheTable.put(key, cacheValue);
    }

    @Override
    public void discard(String key) {
        cacheTable.remove(key);
    }
}