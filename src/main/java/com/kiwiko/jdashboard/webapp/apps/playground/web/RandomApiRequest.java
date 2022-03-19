package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.time.Duration;

public class RandomApiRequest extends JdashboardApiRequest {

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromString("https://api.publicapis.org/random");
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return null;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return String.class;
    }

    private static final class CacheStrategy extends RequestCacheStrategy {
        @Override
        public Duration getCacheDuration() {
            return Duration.ofSeconds(10);
        }
    }
}
