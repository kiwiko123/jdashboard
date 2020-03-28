package com.kiwiko.mvc.resolvers;

import com.kiwiko.mvc.requests.api.RequestBodyParameter;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.json.PropertyObjectMapper;
import com.kiwiko.mvc.json.data.IntermediateJsonBody;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestBodyParameterResolver extends CacheableRequestBodyResolver implements HandlerMethodArgumentResolver {

    @Inject
    private PropertyObjectMapper propertyObjectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyParameter.class);
    }

    @Override
    @Nullable
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

        return propertyObjectMapper.convertValue(value, parameter.getParameterType());
    }
}