package com.kiwiko.jdashboard.webapp.framework.json.deserialization.internal.resolvers;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.interfaces.CustomRequestBody;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.interfaces.RequestBodyDeserializationStrategy;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestError;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Inject private ReflectionHelper reflectionHelper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CustomRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest servletRequest = Optional.ofNullable(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new RequestError("Could not convert web request into HttpServletWebRequest"));
        CustomRequestBody customRequestBody = Optional.ofNullable(methodParameter.getParameterAnnotation(CustomRequestBody.class))
                .orElseThrow(() -> new RequestError("Could not get @CustomRequestBody annotation off of method parameter"));

        RequestBodyDeserializationStrategy deserializationStrategy = reflectionHelper.createDefaultInstance(customRequestBody.strategy());
        String body = getJsonStringFromRequestBody(servletRequest);
        Class<?> targetType = methodParameter.getParameterType();

        return deserializationStrategy.deserialize(body, targetType);
    }

    private String getJsonStringFromRequestBody(ServletRequest request) {
        try {
            return request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RequestError("Failed to read JSON from request body", e);
        }
    }
}
