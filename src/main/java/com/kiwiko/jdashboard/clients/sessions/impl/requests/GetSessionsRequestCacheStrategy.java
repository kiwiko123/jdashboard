package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;

import java.time.Duration;

class GetSessionsRequestCacheStrategy extends RequestCacheStrategy {

    @Override
    public Duration getCacheDuration() {
        return Duration.ofSeconds(1);
    }
}
