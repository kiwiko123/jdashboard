package com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input;

import javax.annotation.Nullable;
import java.time.Duration;

public class PropertyCacheControls {
    private final boolean shouldCache;
    private final @Nullable Duration cacheDuration;

    public PropertyCacheControls(boolean shouldCache, @Nullable Duration cacheDuration) {
        this.shouldCache = shouldCache;
        this.cacheDuration = cacheDuration;
    }

    /**
     * Control whether this property lookup should be cacheable.
     * If true, a property reader will attempt to look up an already-cached value before reading the properties file.
     * If the properties file must be read, the value may subsequently get cached.
     *
     * Otherwise, if false, the property reader will neither attempt an existing look-up nor cache the value after load.
     *
     * By default, this is true.
     *
     * @return true if the property should be cached, or false otherwise
     */
    public boolean getShouldCache() {
        return shouldCache;
    }

    @Nullable
    public Duration getCacheDuration() {
        return cacheDuration;
    }

    public static PropertyCacheControls disabled() {
        return new PropertyCacheControls(false, null);
    }

    public static PropertyCacheControls indefinitely() {
        return new PropertyCacheControls(true, null);
    }

    public static PropertyCacheControls forDuration(Duration cacheDuration) {
        return new PropertyCacheControls(true, cacheDuration);
    }
}
