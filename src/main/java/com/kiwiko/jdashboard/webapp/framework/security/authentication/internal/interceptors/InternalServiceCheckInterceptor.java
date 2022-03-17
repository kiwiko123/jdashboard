package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class InternalServiceCheckInterceptor implements RequestInterceptor {

    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        InternalServiceCheck internalServiceCheck = Optional.ofNullable(method.getMethodAnnotation(InternalServiceCheck.class))
                .orElseGet(() -> method.getMethod().getDeclaringClass().getAnnotation(InternalServiceCheck.class));

        if (internalServiceCheck == null) {
            return true;
        }

        boolean isAuthorized = isInternalServiceAuthorized(request);
        if (!isAuthorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }

        return isAuthorized;
    }

    private boolean isInternalServiceAuthorized(HttpServletRequest request) {
        try {
            internalHttpRequestValidator.validateIncomingRequest(request);
        } catch (UnauthorizedInternalRequestException e) {
            logger.debug(String.format("Unauthorized request attempt to internal service endpoint %s", request.getRequestURL().toString()));
            return false;
        } catch (Exception e) {
            logger.error(String.format("Unexpected exception while attempting to validate internal service request for %s", request.getRequestURL().toString()), e);
            return false;
        }
        return true;
    }
}
