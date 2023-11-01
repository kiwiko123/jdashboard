package com.kiwiko.jdashboard.library.caching.api;

import java.time.Duration;
import java.util.Optional;

public interface ObjectCache {

    /**
     * @param key
     * @param <T> the cached value's type.
     * @return the cached value associated with the given key, if any.
     */
    <T> Optional<T> get(String key);

    /**
     * Indefinitely caches the provided value.
     *
     * @param key the cache key at which the value can be retrieved.
     * @param value the value being cached.
     * @param <T> the value's type.
     * @return the value
     */
    <T> T cache(String key, T value);

    /**
     * Caches the provided value for the given amount of time.
     *
     * @param key the cache key at which the value can be retrieved.
     * @param value the value being cached.
     * @param duration the amount of time from now that the value can be retrieved from the cache.
     * @param <T> the value's type.
     * @return the value
     */
    <T> T cache(String key, T value, Duration duration);

    /**
     * Discards the value associated with the given key.
     * If the key doesn't map to a cached value, does nothing.
     *
     * @param key the key whose value will be discarded.
     * @return the object that was evicted from the cache, or an empty optional if the key did not map to an entry
     */
    <T> Optional<T> evict(String key);
}
