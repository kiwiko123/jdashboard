package com.kiwiko.jdashboard.library.http.client.api.dto.caching;

public class DisabledCacheStrategy extends RequestCacheStrategy {

    @Override
    public boolean shouldCacheRequest() {
        return false;
    }
}
