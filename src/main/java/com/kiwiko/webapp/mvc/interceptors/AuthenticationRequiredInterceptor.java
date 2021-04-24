package com.kiwiko.webapp.mvc.interceptors;

import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.errors.AuthenticationException;
import com.kiwiko.webapp.mvc.security.sessions.api.SessionHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthenticationRequiredInterceptor extends HandlerInterceptorAdapter {

    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private SessionHelper sessionHelper;
    @Inject private Logger logger;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            logger.warn(String.format("Unknown handler type %s", handler.getClass().getName()));
            return false;
        }

        HandlerMethod method = (HandlerMethod) handler;
        AuthenticationRequired authenticationRequired = getAuthenticationRequired(method).orElse(null);
        if (authenticationRequired == null) {
            return true;
        }

        boolean allowRequest = false;
        switch (authenticationRequired.level()) {
            case AUTHENTICATED:
                allowRequest = isActivelyAuthenticated(request);
                break;
            case NONE:
                allowRequest = true;
                break;
        }

        if (!allowRequest) {
            throw new AuthenticationException("User authentication is required.");
        }

        return true;
    }

    private Optional<AuthenticationRequired> getAuthenticationRequired(HandlerMethod method) {
        return Optional.ofNullable(method.getMethodAnnotation(AuthenticationRequired.class))
                .or(() -> Optional.ofNullable(method.getMethod().getDeclaringClass().getAnnotation(AuthenticationRequired.class)));
    }

    private boolean isActivelyAuthenticated(HttpServletRequest request) {
        return sessionRequestHelper.getSessionFromRequest(request)
                .map(session -> !sessionHelper.isExpired(session))
                .orElse(false);
    }
}
