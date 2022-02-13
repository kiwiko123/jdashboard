package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.resolvers;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.AuthenticatedUserException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.InvalidAuthenticatedUserException;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;
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
    @Inject private UserClient userClient;

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

        if (methodParameter.getParameterType() != User.class) {
            throw new IllegalArgumentException(String.format("Incompatible parameter type %s; expected %s", methodParameter.getParameterType().getName(), User.class.getName()));
        }

        HttpSession session = Optional.ofNullable(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .map(HttpServletRequest::getSession)
                .orElseThrow(() -> new InvalidAuthenticatedUserException("No session found for current request"));

        User currentUser = requestContextService.getFromSession(session, SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .map(RequestContext::getUserId)
                .flatMap(userId -> userClient.getById(userId).getUser())
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
