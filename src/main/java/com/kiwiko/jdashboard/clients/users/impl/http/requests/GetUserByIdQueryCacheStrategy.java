package com.kiwiko.jdashboard.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;

import java.time.Duration;

class GetUserByIdQueryCacheStrategy extends RequestCacheStrategy {

    @Override
    public Duration getCacheDuration() {
        return Duration.ofHours(1);
    }
}
