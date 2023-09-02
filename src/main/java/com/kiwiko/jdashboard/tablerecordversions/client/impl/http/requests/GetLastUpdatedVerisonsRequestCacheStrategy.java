package com.kiwiko.jdashboard.tablerecordversions.client.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetLastUpdatedVerisonsRequestCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofMinutes(10);
    }
}
