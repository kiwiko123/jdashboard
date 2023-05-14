package com.kiwiko.jdashboard.webapp.framework.resolvers;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.json.api.JsonMapper;
import com.kiwiko.jdashboard.webapp.framework.json.api.errors.JsonException;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestError;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.library.json.data.IntermediateJsonBody;
import com.kiwiko.jdashboard.webapp.framework.resolvers.data.RequestBodyCacheData;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;
import org.springframework.lang.Nullable;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class CacheableRequestBodyResolver {

    @Inject private JsonMapper jsonMapper;
    @Inject private ObjectCache objectCache;
    @Inject private RequestContextService requestContextService;
    @Inject private Logger logger;

    /**
     * Determines the duration of time to cache a given request's body.
     * Increase this value when debugging!
     *
     * @return the duration of time to cache a given request's body.
     */
    protected Duration getRequestBodyCacheDuration() {
        return Duration.ofSeconds(10);
    }

    /**
     * Given a web request, determine if its request body should be manually deserialized.
     * The request body must be deserialized if either:
     *   1) no {@link RequestContext} is present in its session, then the body must be deserialized, or
     *   2) the {@link RequestContext} indicates that its a different request.
     *
     * @param request
     * @param cacheData
     * @return true if the request body should be manually deserialized, or false if not.
     */
    protected boolean shouldDeserializeFromRequest(HttpServletRequest request, @Nullable RequestBodyCacheData cacheData) {
        Optional<IntermediateJsonBody> cachedBody = Optional.ofNullable(cacheData)
                .map(RequestBodyCacheData::getBody);

        // If there's no cached data, then we have to deserialize it.
        if (!cachedBody.isPresent()) {
            return true;
        }

        RequestContext currentRequestContext = requestContextService.getFromSession(request.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .orElseThrow(() -> new RequestError(String.format("No request context found for %s", request.getRequestURI())));

        return Optional.ofNullable(cacheData)
                .map(RequestBodyCacheData::getRequestContext)

                // If the cached request data refers to a different request than the current one,
                // then we need to deserialize the body.
                .map(context -> !Objects.equals(context, currentRequestContext))
                .orElse(true);
    }

    protected IntermediateJsonBody getDeserializedBodyFromRequest(HttpServletRequest request) {
        String cacheKey = getRequestParameterCacheKey(request);
        RequestBodyCacheData cachedRequestData = (RequestBodyCacheData) objectCache.get(cacheKey)
                .orElse(null);

        IntermediateJsonBody jsonObject;
        if (shouldDeserializeFromRequest(request, cachedRequestData)) {
            // If we know that this is a new request, then deserialize the request body and cache it for another use.
            jsonObject = deserializeRequestBody(request);
            RequestBodyCacheData cacheData = createCacheDataFromRequest(request, jsonObject);
            objectCache.cache(cacheKey, cacheData, getRequestBodyCacheDuration());
        } else {
            // Otherwise, we know that we've just processed this exact web request, so use the cached values.
            jsonObject = cachedRequestData.getBody();
        }

        return jsonObject;
    }

    private IntermediateJsonBody deserializeRequestBody(ServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        String bodyJson = getJsonStringFromRequestBody(request);

        if (bodyJson.isEmpty()) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String requestContextId = requestContextService.getFromSession(httpServletRequest.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                    .map(RequestContext::getId)
                    .map(Object::toString)
                    .orElse("(unknown)");
            logger.warn(String.format("Empty request body when deserializing %s for Request Context %s", httpServletRequest.getRequestURI(), requestContextId));
        } else {
            try {
                body = jsonMapper.deserialize(bodyJson, Map.class);
            } catch (JsonException e) {
                throw new RequestError("Failed to deserialize request body content", e);
            }
        }

        return new IntermediateJsonBody(body);
    }

    private String getJsonStringFromRequestBody(ServletRequest request) {
        String bodyJson;
        try {
            bodyJson = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RequestError("Failed to read JSON from request body", e);
        }

        return bodyJson;
    }

    private String getRequestParameterCacheKey(HttpServletRequest request) {
        RequestContext requestContext = requestContextService.getFromSession(request.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .orElseThrow(() -> new RequestError(String.format("No request context found for %s", request.getRequestURI())));
        return String.format("%s-%s-%d", "cachableRequestBodyParameterResolverKey", requestContext.getUri(), requestContext.getId());
    }

    private RequestBodyCacheData createCacheDataFromRequest(HttpServletRequest request, IntermediateJsonBody body) {
        RequestBodyCacheData cacheData = new RequestBodyCacheData();
        requestContextService.getFromSession(request.getSession(), SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .ifPresent(cacheData::setRequestContext);
        cacheData.setBody(body);
        return cacheData;
    }
}
