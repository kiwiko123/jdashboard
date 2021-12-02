package com.kiwiko.jdashboard.webapp.mvc.resolvers;

import com.kiwiko.jdashboard.webapp.mvc.requests.api.annotations.RequestBodyParameter;
import com.kiwiko.jdashboard.webapp.mvc.requests.api.RequestError;
import com.kiwiko.jdashboard.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.library.json.data.IntermediateJsonBody;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public class RequestBodyParameterResolver extends CacheableRequestBodyResolver implements HandlerMethodArgumentResolver {

    @Inject
    private JsonMapper jsonMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(RequestBodyParameter.class)) {
            return false;
        }

        if (Collection.class.isAssignableFrom(parameter.getParameterType())) {
            throw new IllegalArgumentException(String.format("@RequestBodyParameter doesn't support collections; use @RequestBodyCollectionParameter instead"));
        }

        return true;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {
        RequestBodyParameter requestBodyParameter = Optional.ofNullable(parameter.getParameterAnnotation(RequestBodyParameter.class))
                .orElseThrow(() -> new RequestError(String.format("No @RequestBodyParameter annotation found for \"%s\"", parameter.getParameterName())));
        HttpServletRequest servletRequest = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new RequestError("Could not convert web request into HttpServletWebRequest"));
        IntermediateJsonBody jsonBody = getDeserializedBodyFromRequest(servletRequest);
        Object value = jsonBody.getValue(requestBodyParameter.name())
                .orElse(null);

        if (value == null) {
            if (requestBodyParameter.required()) {
                throw new RequestError(String.format(
                        "Failed to find request body parameter \"%s\" in JSON object: \"%s\"",
                        requestBodyParameter.name(),
                        jsonBody.toString()));
            }
            return null;
        }

        return jsonMapper.convertValue(value, parameter.getParameterType());
    }
}