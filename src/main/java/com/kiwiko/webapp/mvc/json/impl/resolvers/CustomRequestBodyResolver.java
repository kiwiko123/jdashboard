package com.kiwiko.webapp.mvc.json.impl.resolvers;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.webapp.mvc.json.api.CustomRequestBodySerializationStrategy;
import com.kiwiko.webapp.mvc.json.api.annotations.CustomRequestBody;
import com.kiwiko.webapp.mvc.json.api.errors.JsonException;
import com.kiwiko.webapp.mvc.json.data.IntermediateJsonBody;
import com.kiwiko.webapp.mvc.requests.api.RequestError;
import com.kiwiko.webapp.mvc.resolvers.CacheableRequestBodyResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CustomRequestBodyResolver extends CacheableRequestBodyResolver implements HandlerMethodArgumentResolver {

    @Inject private ReflectionHelper reflectionHelper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CustomRequestBody.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest servletRequest = Optional.ofNullable(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new RequestError("Could not convert web request into HttpServletWebRequest"));
        CustomRequestBody customRequestBody = Optional.ofNullable(methodParameter.getParameterAnnotation(CustomRequestBody.class))
                .orElseThrow(() -> new JsonException("Failed to get CustomRequestBody parameter annotation"));

        CustomRequestBodySerializationStrategy strategy = reflectionHelper.createDefaultInstance(customRequestBody.strategy());
        IntermediateJsonBody body = getDeserializedBodyFromRequest(servletRequest);
        Class<?> targetType = methodParameter.getParameterType();

        return strategy.deserialize(body, reflectionHelper.createDefaultInstance(targetType));
    }
}
