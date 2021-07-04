package com.kiwiko.webapp.mvc.requests.internal;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;

public class InMemoryRequestContextService {

    public static final String REQUEST_CONTEXT_SESSION_KEY = "requestContext";
    private static final String keyIdentifierPrefix = "request-context-";

    @Inject
    private ObjectCache objectCache;

    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public Optional<RequestContext> getRequestContext(String requestUrl) {
        String requestKey = getRequestKey(requestUrl);
        return objectCache.<RequestContext>get(requestKey)
                .filter(requestContext -> requestContext.getEndTime().isEmpty());
    }

    public Optional<RequestContext> getRequestContext(HttpServletRequest request) {
        String requestUrl = getRequestUri(request);
        return getRequestContext(requestUrl);
    }

    public void saveRequestContext(RequestContext context) {
        String requestKey = getRequestKey(context.getUri());
        objectCache.cache(requestKey, context, Duration.ofDays(1));
    }

    private String getRequestKey(String requestUrl) {
        return String.format("%s%s", keyIdentifierPrefix, requestUrl);
    }
}
