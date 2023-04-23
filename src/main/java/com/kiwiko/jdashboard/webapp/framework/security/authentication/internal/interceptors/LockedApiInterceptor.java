package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
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
import java.util.Set;

public class LockedApiInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockedApiInterceptor.class);

    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        LockedApi lockedApi = Optional.ofNullable(AnnotationUtils.findAnnotation(method.getMethod(), LockedApi.class))
                .orElseGet(() -> AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), LockedApi.class));

        if (lockedApi == null) {
            return true;
        }

        boolean isAuthorized = isInternalServiceAuthorized(request, lockedApi);
        if (!isAuthorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }

        return isAuthorized;
    }

    private boolean isInternalServiceAuthorized(HttpServletRequest request, LockedApi lockedApi) {
        Set<String> authorizedServiceClientIdentifiers = Set.of(lockedApi.clients());
        try {
            internalHttpRequestValidator.validateIncomingRequest(request, authorizedServiceClientIdentifiers);
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
