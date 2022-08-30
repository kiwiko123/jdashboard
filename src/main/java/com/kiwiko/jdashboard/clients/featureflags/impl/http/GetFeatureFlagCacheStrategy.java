package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetFeatureFlagCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofSeconds(30);
    }
}
