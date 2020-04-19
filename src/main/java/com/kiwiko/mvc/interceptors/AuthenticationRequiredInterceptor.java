package com.kiwiko.mvc.interceptors;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.mvc.security.sessions.api.SessionHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationRequiredInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private SessionRequestHelper sessionRequestHelper;

    @Inject
    private SessionHelper sessionHelper;

    @Inject
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            logService.warn(String.format("Unknown handler type %s", handler.getClass().getName()));
            return false;
        }

        HandlerMethod method = (HandlerMethod) handler;
        if (!requiresAuthentication(method)) {
            return true;
        }

        return sessionRequestHelper.getSessionFromRequest(request)
                .map(session -> !sessionHelper.isExpired(session))
                .orElse(false);
    }

    private boolean requiresAuthentication(HandlerMethod method) {
        if (method.getMethodAnnotation(AuthenticationRequired.class) != null) {
            return true;
        }

        return method.getMethod().getDeclaringClass().getAnnotation(AuthenticationRequired.class) != null;
    }
}
