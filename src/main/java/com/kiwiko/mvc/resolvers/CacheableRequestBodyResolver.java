package com.kiwiko.mvc.resolvers;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.mvc.json.api.JsonMapper;
import com.kiwiko.mvc.json.api.errors.JsonException;
import com.kiwiko.mvc.requests.api.RequestContextService;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.json.data.IntermediateJsonBody;
import com.kiwiko.mvc.resolvers.data.RequestBodyCacheData;
import com.kiwiko.mvc.security.sessions.data.SessionProperties;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class CacheableRequestBodyResolver {

    @Inject
    private JsonMapper jsonMapper;

    @Inject
    private CacheService cacheService;

    @Inject
    private RequestContextService requestContextService;

    /**
     * Determines the duration of time to cache a given request's body.
     * Increase this value when debugging!
     *
     * @return the duration of time to cache a given request's body.
     */
    protected TemporalAmount getRequestBodyCacheDuration() {
        return Duration.ofSeconds(3);
    }

    /**
     * Given a web request, determine if its request body should be manually deserialized.
     * The request body must be deserialized if either:
     *   1) no {@link RequestContext} is present in its session, then the body must be deserialized, or
     *   2) the {@link RequestContext} indicates that its a different request.
     *
     * @param request
     * @return true if the request body should be manually deserialized, or false if not.
     */
    protected boolean shouldDeserializeFromRequest(HttpServletRequest request) {
        RequestContext currentRequestContext = requestContextService.getFromSession(request.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .orElse(null);

        if (currentRequestContext == null) {
            return true;
        }

        String key = getRequestParameterCacheKey(request);
        return cacheService.get(key, RequestBodyCacheData.class)
                .map(RequestBodyCacheData::getRequestContext)
                .map(context -> !Objects.equals(context, currentRequestContext))
                .orElse(true);
    }

    protected IntermediateJsonBody getDeserializedBodyFromRequest(HttpServletRequest request) {
        String cacheKey = getRequestParameterCacheKey(request);
        Optional<IntermediateJsonBody> cachedParameters = cacheService.get(cacheKey, RequestBodyCacheData.class)
                .map(RequestBodyCacheData::getBody);

        IntermediateJsonBody jsonObject;

        // If we know that we've just processed this exact web request,
        // then use the cached values.
        if (cachedParameters.isPresent() && !shouldDeserializeFromRequest(request)) {
            jsonObject = cachedParameters.get();
        } else {
            // Otherwise, deserialize the request body and cache it for another use.
            jsonObject = deserializeRequestBody(request);
            RequestBodyCacheData cacheData = createCacheDataFromRequest(request, jsonObject);
            cacheService.cache(cacheKey, cacheData, getRequestBodyCacheDuration());
        }

        return jsonObject;
    }

    private IntermediateJsonBody deserializeRequestBody(ServletRequest request) {
        String bodyJson = getJsonStringFromRequestBody(request);
        Map<String, Object> body;

        try {
            body = jsonMapper.deserialize(bodyJson, Map.class);
        } catch (JsonException e) {
            throw new RequestError("Failed to deserialize request body content", e);
        }

        return new IntermediateJsonBody(body);
    }

    private String getJsonStringFromRequestBody(ServletRequest request) {
        String bodyJson;
        try {
            bodyJson = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            request.getReader().reset();
        } catch (IOException e) {
            throw new RequestError("Failed to read JSON from request body", e);
        }

        return bodyJson;
    }

    private String getRequestParameterCacheKey(HttpServletRequest request) {
        return String.format("%s-%s", "cachableRequestBodyParameterResolverKey", request.getRequestURI());
    }

    private RequestBodyCacheData createCacheDataFromRequest(HttpServletRequest request, IntermediateJsonBody body) {
        RequestBodyCacheData cacheData = new RequestBodyCacheData();
        requestContextService.getFromSession(request.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .ifPresent(cacheData::setRequestContext);
        cacheData.setBody(body);
        return cacheData;
    }
}
