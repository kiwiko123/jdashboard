package com.kiwiko.memory.caching.internal;

import com.kiwiko.memory.caching.api.CacheService;

import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.function.Supplier;

abstract class UtilityCacheService implements CacheService {

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        return get(key).map(clazz::cast);
    }

    @Override
    public <T> void cache(String key, T value) {
        cache(key, value, null);
    }

    @Override
    public <T> T cache(String key, Supplier<T> valueSupplier) {
        return cache(key, valueSupplier, null);
    }

    @Override
    public <T> T cache(String key, Supplier<T> valueSupplier, TemporalAmount duration) {
        T value = valueSupplier.get();
        cache(key, value, duration);
        return value;
    }

    @Override
    public <T> T getOrCache(String key, Supplier<T> valueSupplier, TemporalAmount duration) {
        return (T) get(key).orElseGet(() -> cache(key, valueSupplier, duration));
    }

    @Override
    public <T> T getOrCache(String key, Supplier<T> valueSupplier) {
        return getOrCache(key, valueSupplier, null);
    }
}