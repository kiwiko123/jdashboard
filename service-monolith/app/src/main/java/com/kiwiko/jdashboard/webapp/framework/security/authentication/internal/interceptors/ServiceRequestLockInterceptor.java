package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.ServiceRequestLock;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ServiceRequestLockInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRequestLockInterceptor.class);

    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        ServiceRequestLock serviceRequestLock = Optional.ofNullable(AnnotationUtils.findAnnotation(method.getMethod(), ServiceRequestLock.class))
                .orElseGet(() -> AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), ServiceRequestLock.class));

        if (serviceRequestLock == null) {
            return true;
        }

        boolean isAuthorized = isInternalServiceAuthorized(request, serviceRequestLock);
        if (!isAuthorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }

        return isAuthorized;
    }

    private boolean isInternalServiceAuthorized(HttpServletRequest request, ServiceRequestLock serviceRequestLock) {
        try {
            internalHttpRequestValidator.validateIncomingRequest(request, serviceRequestLock);
        } catch (UnauthorizedInternalRequestException e) {
            LOGGER.warn("Unauthorized request attempt to internal service endpoint {}", request.getRequestURL(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected exception while attempting to validate internal service request for {}", request.getRequestURL(), e);
            return false;
        }
        return true;
    }
}
