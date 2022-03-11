package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;

import java.time.Duration;
import java.util.Optional;

class GetSessionsRequestCacheStrategy extends RequestCacheStrategy {

    @Override
    public Optional<Duration> getCacheDuration() {
        return Optional.of(Duration.ofSeconds(1));
    }
}
