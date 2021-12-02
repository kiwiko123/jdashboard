package com.kiwiko.jdashboard.webapp.framework.resolvers;

import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestError;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.SessionProperties;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Resolver that allows the retrieval of a {@link RequestContext} object by way of method argument.
 * For any method represented by a {@link org.springframework.web.bind.annotation.RequestMapping},
 * add an argument of type {@link RequestContext} to gain information about the current request.
 */
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
        HttpServletRequest httpServletRequest = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(() -> new RequestError("Failed to create HttpServletRequest"));

        return Optional.ofNullable(httpServletRequest.getSession())
                .map(session -> session.getAttribute(SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY))
                .map(requestContextId -> (Long) requestContextId)
                .flatMap(requestContextService::getById)
                .orElseThrow(() -> new RequestError(
                        String.format("Failed to find RequestContext for \"%s\"", httpServletRequest.getRequestURI())));
    }
}
