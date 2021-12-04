package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.resolvers;

import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.AuthenticatedUserException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.InvalidAuthenticatedUserException;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.SessionProperties;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Inject private RequestContextService requestContextService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(AuthenticatedUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {
        AuthenticatedUser authenticatedUser = methodParameter.getParameterAnnotation(AuthenticatedUser.class);
        if (authenticatedUser == null) {
            throw new InvalidAuthenticatedUserException("No @AuthenticatedUser annotation found");
        }

        HttpSession session = Optional.ofNullable(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .map(HttpServletRequest::getSession)
                .orElseThrow(() -> new InvalidAuthenticatedUserException("No session found for current request"));

        User currentUser = requestContextService.getFromSession(session, SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .flatMap(RequestContext::getUser)
                .orElse(null);

        if (currentUser != null) {
            return currentUser;
        }

        if (authenticatedUser.required()) {
            throw new AuthenticatedUserException("No currently authenticated user found");
        }

        Class<?> parameterType = methodParameter.getParameterType();
        if (parameterType == User.class) {
            return null;
        }

        if (parameterType == Optional.class) {
            return Optional.empty();
        }

        throw new InvalidAuthenticatedUserException(String.format("Invalid type with @AuthenticatedUser: %s", parameterType));
    }
}
