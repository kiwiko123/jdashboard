package com.kiwiko.jdashboard.library.http.client.caching;

public final class RequestCacheStrategies {
    public static final RequestCacheStrategy DISABLED = new DisabledCacheStrategy();
    public static final RequestCacheStrategy INDEFINITE = new IndefiniteCacheStrategy();

    private RequestCacheStrategies() {}
}
