package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors;

import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.interceptors.internal.SessionRequestHelper;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.api.SessionHelper;
import com.kiwiko.jdashboard.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UserAuthCheckInterceptor implements EndpointInterceptor {

    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private SessionHelper sessionHelper;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        UserAuthCheck userAuthCheck = Optional.ofNullable(method.getMethodAnnotation(UserAuthCheck.class))
                .orElseGet(() -> method.getMethod().getDeclaringClass().getAnnotation(UserAuthCheck.class));

        if (userAuthCheck == null) {
            return true;
        }

        boolean isAuthenticated = sessionRequestHelper.getSessionFromRequest(request)
                .map(session -> !sessionHelper.isExpired(session))
                .orElse(false);

        if (!isAuthenticated) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return isAuthenticated;
    }
}
