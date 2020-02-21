package com.kiwiko.mvc.requests.api;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.mvc.requests.data.RequestContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;

public class RequestContextService {

    private static final String keyIdentifierPrefix = "request-context-";

    @Inject
    private CacheService cacheService;

    public String getRequestUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public String getRequestKey(HttpServletRequest request) {
        String requestUrl = getRequestUrl(request);
        return getRequestKey(requestUrl);
    }

    public String getRequestKey(String requestUrl) {
        return String.format("%s%s", keyIdentifierPrefix, requestUrl);
    }

    public Optional<RequestContext> getRequestContext(String requestUrl) {
        String requestKey = getRequestKey(requestUrl);
        return cacheService.get(requestKey);
    }

    public void saveRequestContext(String requestUrl, RequestContext context) {
        String requestKey = getRequestKey(requestUrl);
        cacheService.cache(requestKey, context, Duration.ofDays(1));
    }
}
