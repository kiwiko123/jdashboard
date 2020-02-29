package com.kiwiko.mvc.resolvers;

import com.kiwiko.mvc.annotations.RequestBodyCollectionParameter;
import com.kiwiko.mvc.json.PropertyObjectMapper;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.json.data.IntermediateJsonBody;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Optional;

public class RequestBodyCollectionParameterResolver extends CacheableRequestBodyResolver implements HandlerMethodArgumentResolver {

    @Inject
    private PropertyObjectMapper propertyObjectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!Collection.class.isAssignableFrom(parameter.getParameterType())) {
            throw new RequestError(String.format("Parameter type must be a subclass of %s, not %s", Collection.class.getName(), parameter.getParameterType().getCanonicalName()));
        }
        return parameter.hasParameterAnnotation(RequestBodyCollectionParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {
        RequestBodyCollectionParameter collectionParameter = Optional.ofNullable(parameter.getParameterAnnotation(RequestBodyCollectionParameter.class))
                .orElseThrow(() -> new RequestError(String.format("No @CollectionParameter annotation found for \"%s\"", parameter.getParameterName())));
        HttpServletRequest servletRequest = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new RequestError("Could not convert web request into HttpServletWebRequest"));
        IntermediateJsonBody jsonBody = getDeserializedBodyFromRequest(servletRequest);
        Object value = jsonBody.getValue(collectionParameter.name())
                .orElse(null);

        if (value == null) {
            if (collectionParameter.required()) {
                throw new RequestError(String.format(
                        "Failed to find request body parameter \"%s\" in JSON object: \"%s\"",
                        collectionParameter.name(),
                        jsonBody.toString()));
            }
            return parameter.getParameterType().getDeclaredConstructor().newInstance();
        }

        return propertyObjectMapper.convertCollectionValue(
                value,
                parameter.getParameterType().asSubclass(Collection.class),
                collectionParameter.valueType());
    }
}
