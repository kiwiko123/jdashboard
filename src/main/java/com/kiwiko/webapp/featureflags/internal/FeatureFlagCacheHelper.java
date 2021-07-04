package com.kiwiko.webapp.featureflags.internal;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

public class FeatureFlagCacheHelper {
    static final TemporalAmount DEFAULT_FLAG_CACHE_DURATION = Duration.ofHours(1);
    @Inject private ObjectCache objectCache;

    void cacheFlag(FeatureFlag flag) {
        String key = makeFlagCacheKey(flag.getName(), flag.getUserId());
        objectCache.cache(key, flag, DEFAULT_FLAG_CACHE_DURATION);
    }

    void invalidateCachedFlag(FeatureFlag flag) {
        String key = makeFlagCacheKey(flag.getName(), flag.getUserId());
        objectCache.invalidate(key);
    }

    Optional<FeatureFlag> getCachedFlag(String flagName, @Nullable Long userId) {
        String key = makeFlagCacheKey(flagName, userId);
        return objectCache.get(key);
    }

    String makeFlagCacheKey(String flagName, @Nullable Long userId) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s.flags.name.%s", HybridFeatureFlagService.class.getName(), flagName));

        if (userId != null) {
            builder.append(String.format(".users.id.%d", userId));
        }

        return builder.toString();
    }
}
