package com.kiwiko.persistence.entities;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.persistence.identification.GeneratedLongIdentifiable;

import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.TemporalAmount;

public abstract class InMemoryDataEntity extends GeneratedLongIdentifiable implements DataEntity {

    protected final TemporalAmount timeToLive = Duration.ofHours(1);

    @Inject
    private CacheService cacheService;

    @Override
    public void save() {
        String key = getKey();
        cacheService.cache(key, this, timeToLive);
    }

    @Override
    public void delete() {
        String key = getKey();
        cacheService.discard(key);
    }

    private String getKey() {
        return getClass().getName();
    }
}
