package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetLastUpdatedVerisonsRequestCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofMinutes(10);
    }
}
