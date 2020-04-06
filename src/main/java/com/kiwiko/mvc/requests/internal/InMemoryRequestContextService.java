package com.kiwiko.mvc.requests.internal;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.mvc.requests.data.RequestContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;

public class InMemoryRequestContextService {

    public static final String REQUEST_CONTEXT_SESSION_KEY = "requestContext";
    private static final String keyIdentifierPrefix = "request-context-";

    @Inject
    private CacheService cacheService;

    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public Optional<RequestContext> getRequestContext(String requestUrl) {
        String requestKey = getRequestKey(requestUrl);
        return cacheService.get(requestKey, RequestContext.class)
                .filter(requestContext -> !requestContext.getEndTime().isPresent());
    }

    public Optional<RequestContext> getRequestContext(HttpServletRequest request) {
        String requestUrl = getRequestUri(request);
        return getRequestContext(requestUrl);
    }

    public void saveRequestContext(RequestContext context) {
        String requestKey = getRequestKey(context.getUri());
        cacheService.cache(requestKey, context, Duration.ofDays(1));
    }

    private String getRequestKey(String requestUrl) {
        return String.format("%s%s", keyIdentifierPrefix, requestUrl);
    }
}
