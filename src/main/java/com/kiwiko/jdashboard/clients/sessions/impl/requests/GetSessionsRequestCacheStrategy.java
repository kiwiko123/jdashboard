package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetSessionsRequestCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofSeconds(3);
    }
}
