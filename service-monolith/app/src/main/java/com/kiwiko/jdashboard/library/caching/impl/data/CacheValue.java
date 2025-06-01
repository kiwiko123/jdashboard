package com.kiwiko.jdashboard.library.caching.impl.data;

import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.Duration;

@Getter
@ToString
public class CacheValue<T> {
    private final T value;
    private final @Nullable Long expirationTimeMs;

    public CacheValue(T value, @Nullable Duration duration) {
        this.value = value;
        expirationTimeMs = duration == null ? null : System.currentTimeMillis() + duration.toMillis();
    }
}