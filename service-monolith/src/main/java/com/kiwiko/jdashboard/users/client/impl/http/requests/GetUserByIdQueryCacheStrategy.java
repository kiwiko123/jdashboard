package com.kiwiko.jdashboard.users.client.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import java.time.Duration;

class GetUserByIdQueryCacheStrategy extends RequestCacheStrategy {

    @Override
    public Duration getCacheDuration() {
        return Duration.ofHours(1);
    }
}