package com.kiwiko.jdashboard.library.http.client.api.dto;

import java.time.Duration;
import java.util.Optional;

public class DisabledCacheStrategy extends RequestCacheStrategy {

    @Override
    public Optional<Duration> getCacheDuration() {
        return Optional.empty();
    }
}
