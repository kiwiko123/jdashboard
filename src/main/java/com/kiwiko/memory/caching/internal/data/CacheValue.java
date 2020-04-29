package com.kiwiko.memory.caching.internal.data;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

public class CacheValue<T> {

    private final T value;
    private final Instant createdTime;
    private final Optional<Instant> expirationTime;

    public CacheValue(T value, @Nullable TemporalAmount duration) {
        final Instant now = Instant.now();

        this.value = value;
        createdTime = now;
        expirationTime = Optional.ofNullable(duration).map(now::plus);
    }

    public T getValue() {
        return value;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Optional<Instant> getExpirationTime() {
        return expirationTime;
    }

    @Override
    public String toString() {
        return String.format("CacheValue(%s, %s, %s)", value.toString(), createdTime.toString(), expirationTime.toString());
    }
}