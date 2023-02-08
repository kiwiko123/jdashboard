package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests;

import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;

import javax.annotation.Nullable;
import java.time.Duration;

class GetTableRecordVerisonsRequestCacheStrategy extends RequestCacheStrategy {

    @Nullable
    @Override
    public Duration getCacheDuration() {
        return Duration.ofMinutes(10);
    }
}
