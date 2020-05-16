package com.kiwiko.webapp.mvc.resolvers;

import com.kiwiko.webapp.mvc.requests.api.annotations.RequestBodyCollectionParameter;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.requests.api.RequestError;
import com.kiwiko.webapp.mvc.json.data.IntermediateJsonBody;
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

public class RequestBodyCollectionParameterResolver extends CacheableRequestBodyResolver implements HandlerMethodArgumentResolver {

    @Inject
    private JsonMapper jsonMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestBodyCollectionParameter.class)) {
            if (Collection.class.isAssignableFrom(parameter.getParameterType())) {
                return true;
            }
            throw new RequestError(String.format("Parameter type must be a subclass of %s, not %s", Collection.class.getName(), parameter.getParameterType().getCanonicalName()));
        }
        return false;
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

            // If the value is neither present nor required, return an empty collection.
            return parameter.getParameterType().getDeclaredConstructor().newInstance();
        }

        return jsonMapper.convertCollectionValue(
                value,
                parameter.getParameterType().asSubclass(Collection.class),
                collectionParameter.valueType());
    }
}
