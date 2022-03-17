package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class TestPostApiRequest extends DefaultApiRequest {

    private final User user;

    public TestPostApiRequest(User user) {
        this.user = user;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath("/playground-api/test"));
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new CacheStrategy();
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return user;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Override
    public Duration getRequestTimeout() {
        return Duration.ofMinutes(10);
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
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
