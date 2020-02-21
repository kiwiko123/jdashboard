package com.kiwiko.mvc.resolvers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.mvc.annotations.RequestBodyParameter;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.json.PropertyObjectMapper;
import com.kiwiko.mvc.requests.api.RequestContextService;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestBodyParameterResolver implements HandlerMethodArgumentResolver {

    private static final String requestContextKey = "requestContext";

    @Inject
    private CacheService cacheService;

    @Inject
    private RequestContextService requestContextService;

    @Inject
    private PropertyObjectMapper propertyObjectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyParameter.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) throws Exception {
        RequestBodyParameter requestBodyParameter = parameter.getParameterAnnotation(RequestBodyParameter.class);
        if (requestBodyParameter == null) {
            throw new TypeNotPresentException(String.format("No @RequestBodyParameter annotation found for \"%s\"", parameter.getParameterName()), null);
        }

        HttpServletRequest httpServletRequest = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new IllegalArgumentException("Could not convert web request into HttpServletWebRequest"));
        String cacheKey = getRequestParameterCacheKey(httpServletRequest);
        Optional<Map<String, Object>> cachedParameters = cacheService.get(cacheKey);
        Map<String, Object> jsonObject;

        // If we know that we've just processed this exact web request,
        // then use the cached values.
        if (cachedParameters.isPresent() && !shouldDeserializeFromRequest(httpServletRequest)) {
            jsonObject = cachedParameters.get();
        } else {
            // Otherwise, deserialize the request body and cache it for another use.
            jsonObject = deserializeFromRequest(httpServletRequest);
            cacheService.cache(cacheKey, jsonObject, Duration.ofMinutes(1));
        }

        if (!jsonObject.containsKey(requestBodyParameter.name())) {
            if (requestBodyParameter.required()) {
                throw new IllegalArgumentException(String.format(
                        "Failed to find request body parameter \"%s\" in JSON object: \"%s\"",
                        requestBodyParameter.name(),
                        jsonObject.toString()));
            }
            return null;
        }

        // Set the RequestContext object for this request in the request object's session.
        // The RequestContext can be consulted in subsequent resolvers to determine if it's the same request.
        setRequestContextInSession(httpServletRequest);

        Object value = jsonObject.get(requestBodyParameter.name());
        return propertyObjectMapper.convertValue(value, parameter.getParameterType());
    }

    private Map<String, Object> deserializeFromRequest(HttpServletRequest request) {
        String bodyJson = "";

        try {
            bodyJson = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

        try {
            return propertyObjectMapper.readValue(bodyJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e.getMessage());
        }
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
    private boolean shouldDeserializeFromRequest(HttpServletRequest request) {
        Optional<RequestContext> currentRequestContext = Optional.of(request.getSession())
                .map(session -> session.getAttribute(requestContextKey))
                .map(requestContext -> (RequestContext) requestContext);

        if (!currentRequestContext.isPresent()) {
            return true;
        }

        String requestUrl = requestContextService.getRequestUrl(request);
        return requestContextService.getRequestContext(requestUrl)
                .map(context -> !Objects.equals(context, currentRequestContext.get()))
                .orElse(true);
    }

    private void setRequestContextInSession(HttpServletRequest request) {
        String requestUrl = requestContextService.getRequestUrl(request);
        RequestContext requestContext = requestContextService.getRequestContext(requestUrl)
                .orElseThrow(() -> new IllegalStateException(String.format("No request context saved for \"%s\"", requestUrl)));
        request.getSession().setAttribute(requestContextKey, requestContext);
    }

    private String getRequestParameterCacheKey(HttpServletRequest request) {
        return String.format("%s-%s", "requestBodyParameterResolverKey", request.getRequestURI());
    }
}