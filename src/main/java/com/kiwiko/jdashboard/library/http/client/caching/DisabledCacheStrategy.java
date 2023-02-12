package com.kiwiko.jdashboard.library.http.client.caching;

public class DisabledCacheStrategy extends RequestCacheStrategy {

    @Override
    public boolean shouldCacheRequest() {
        return false;
    }
}
