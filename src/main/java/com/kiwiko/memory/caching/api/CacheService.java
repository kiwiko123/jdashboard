package com.kiwiko.memory.caching.api;

import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.function.Supplier;

public interface CacheService {

    /**
     * @param key
     * @param clazz the type of T, to which the cached value will be cast.
     * @param <T> the cached value's type.
     * @return the cached value associated with the given key, if any.
     */
    <T> Optional<T> get(String key, Class<T> clazz);
    <T> Optional<T> get(String key);

    /**
     * Indefinitely caches the provided value.
     *
     * @param key the cache key at which the value can be retrieved.
     * @param value the value being cached.
     * @param <T> the value's type.
     */
    <T> void cache(String key, T value);

    /**
     * Caches the provided value for the given amount of time.
     *
     * @param key the cache key at which the value can be retrieved.
     * @param value the value being cached.
     * @param duration the amount of time from now that the value can be retrieved from the cache.
     * @param <T> the value's type.
     */
    <T> void cache(String key, T value, TemporalAmount duration);

    /**
     * Indefinitely caches the result of invoking the given supplier.
     *
     * @see #cache(String, Object)
     * @return the result of invoking the supplier.
     */
    <T> T cache(String key, Supplier<T> valueSupplier);

    /**
     * Caches the result of invoking the given supplier for the given amount of time.
     *
     * @see #cache(String, Object, TemporalAmount)
     * @return the result of invoking the supplier.
     */
    <T> T cache(String key, Supplier<T> valueSupplier, TemporalAmount duration);

    /**
     * Retrieves the cached value associated with the given key, if it's available.
     * If it's not available (i.e., doesn't exist, or has since expired),
     * then cache and return the result of invoking the given supplier.
     *
     * @param key
     * @param valueSupplier the supplier that will be invoked if no cached value is available.
     * @param <T> the value's type.
     * @return the cached value at the given key, or the result of invoking the given supplier.
     */
    <T> T getOrCache(String key, Supplier<T> valueSupplier);

    /**
     * @see #getOrCache(String, Supplier)
     */
    <T> T getOrCache(String key, Supplier<T> valueSupplier, TemporalAmount duration);

    /**
     * Discards the value associated with the given key.
     * If the key doesn't map to a cached value, does nothing.
     *
     * @param key the key whose value will be discarded.
     */
    void discard(String key);
}