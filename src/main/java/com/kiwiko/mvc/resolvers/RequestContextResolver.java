package com.kiwiko.mvc.resolvers;

import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.api.RequestContextService;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestContextResolver implements HandlerMethodArgumentResolver {

    @Inject
    private RequestContextService requestContextService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == RequestContext.class;
    }

    public Object resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .map(requestContextService::getRequestUrl)
                .flatMap(requestContextService::getRequestContext)
                .orElseThrow(() -> new IllegalStateException("Failed to find RequestContext for web request"));
    }
}
