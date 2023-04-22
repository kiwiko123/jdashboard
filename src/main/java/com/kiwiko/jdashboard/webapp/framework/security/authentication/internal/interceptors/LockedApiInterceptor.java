package com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.AuthorizedServiceClients;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

public class AuthorizedServiceClientsInterceptor implements RequestInterceptor {

    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        AuthorizedServiceClients authorizedServiceClients = Optional.ofNullable(AnnotationUtils.findAnnotation(method.getMethod(), AuthorizedServiceClients.class))
                .orElseGet(() -> AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), AuthorizedServiceClients.class));

        if (authorizedServiceClients == null) {
            return true;
        }

        boolean isAuthorized = isInternalServiceAuthorized(request, authorizedServiceClients);
        if (!isAuthorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }

        return isAuthorized;
    }

    private boolean isInternalServiceAuthorized(HttpServletRequest request, AuthorizedServiceClients authorizedServiceClients) {
        Set<String> authorizedServiceClientIdentifiers = Set.of(authorizedServiceClients.value());
        try {
            internalHttpRequestValidator.validateIncomingRequest(request, authorizedServiceClientIdentifiers);
        } catch (UnauthorizedInternalRequestException e) {
            logger.warn("Unauthorized request attempt to internal service endpoint {}", request.getRequestURL(), e);
            return false;
        } catch (Exception e) {
            logger.error("Unexpected exception while attempting to validate internal service request for {}", request.getRequestURL(), e);
            return false;
        }
        return true;
    }
}
