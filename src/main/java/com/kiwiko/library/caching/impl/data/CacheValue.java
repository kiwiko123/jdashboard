package com.kiwiko.library.caching.impl.data;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

public class CacheValue<T> {

    private final T value;
    private final Instant createdTime;
    private final @Nullable Instant expirationTime;

    public CacheValue(T value, @Nullable TemporalAmount duration) {
        final Instant now = Instant.now();

        this.value = value;
        createdTime = now;
        expirationTime = Optional.ofNullable(duration)
                .map(now::plus)
                .orElse(null);
    }

    public T getValue() {
        return value;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Optional<Instant> getExpirationTime() {
        return Optional.ofNullable(expirationTime);
    }

    @Override
    public String toString() {
        String expirationDisplay = getExpirationTime()
                .map(Object::toString)
                .orElse("(indefinite)");
        return String.format(
                "CacheValue(value=%s,createdTime=%s,expirationTime=%s)",
                value,
                createdTime.toString(),
                expirationDisplay);
    }
}