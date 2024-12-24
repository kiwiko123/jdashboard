package com.kiwiko.jdashboard.webapp.caching.impl.lru;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.caching.impl.data.CacheValue;
import com.kiwiko.jdashboard.library.dataStructures.collections.sets.lru.CustomizableActionLeastRecentlyUsedSet;
import com.kiwiko.jdashboard.library.dataStructures.collections.sets.lru.LeastRecentlyUsedSetActions;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PersistentLeastRecentlyUsedObjectCache implements ObjectCache {

    private CustomizableActionLeastRecentlyUsedSet<String> keyCache;
    private Map<String, CacheValue<?>> table;
    private Logger logger;

    @Inject
    public PersistentLeastRecentlyUsedObjectCache(Logger logger) {
        this.logger = logger;

        LeastRecentlyUsedSetActions<String> actions = new LeastRecentlyUsedSetActions<>();
        actions.setOnContains(e -> logCacheOperation(String.format("Attempting to access key: %s", e)));
        actions.setOnAdd(e -> logCacheOperation(String.format("Caching key: %s", e)));
        actions.setOnRemove(e -> logCacheOperation(String.format("Invalidating cached key: %s", e)));
        actions.setOnEvict(this::onEvict);

        keyCache = new CustomizableActionLeastRecentlyUsedSet<>();
        keyCache.setActions(actions);
        keyCache.setCapacity(5);

        table = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Optional<T> get(String key) {
        if (!keyCache.contains(key)) {
            return Optional.empty();
        }

        CacheValue<?> value = table.get(key);
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
        keyCache.add(key);
        table.put(key, cacheValue);
        return value;
    }

    @Override
    public <T> Optional<T> evict(String key) {
        keyCache.remove(key);
        @SuppressWarnings("unchecked")
        T evictedValue = (T) table.remove(key);
        return Optional.ofNullable(evictedValue);
    }

    private void onEvict(String key) {
        CacheValue<?> cacheValue = table.get(key);
        if (cacheValue == null) {
            logger.error(String.format("Unable to find cached value for key %s on eviction", key));
            return;
        }

        logCacheOperation(String.format("Persisting evicted key/value: %s, %s", key, cacheValue.toString()));
        // TODO persist cache value

        evict(key);
    }

    private void logCacheOperation(String message) {
        String fullMessage = String.format("%s: %s", getClass().getSimpleName(), message);
        logger.debug(fullMessage);
    }
}
