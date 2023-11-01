package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetFeatureFlagCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofSeconds(5);
    }
}
