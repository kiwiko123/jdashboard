package com.kiwiko.jdashboard.webapp.clients.users.impl.requests;

import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.caching.RequestCachePolicy;

import java.time.Duration;

public class GetUsersQueryRequest extends GetRequest {
    private static final String BASE_API_URL = "/users/api";
    private static final RequestCachePolicy CACHE_POLICY = RequestCachePolicy.newBuilder()
            .setDuration(Duration.ofMinutes(10))
            .build();
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public GetUsersQueryRequest(String queryJson) {
        setUrl(String.format("%s?query=%s", BASE_API_URL, queryJson));
        setCachePolicy(CACHE_POLICY);
        setTimeout(TIMEOUT);
        setInternalServiceRequest(true);
    }
}
