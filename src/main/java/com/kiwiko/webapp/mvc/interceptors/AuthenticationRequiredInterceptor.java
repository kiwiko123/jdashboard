package com.kiwiko.webapp.mvc.interceptors;

import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.mvc.interceptors.api.EndpointInterceptor;
import com.kiwiko.webapp.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.errors.AuthenticationException;
import com.kiwiko.webapp.mvc.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.webapp.mvc.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.webapp.mvc.security.sessions.api.SessionHelper;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthenticationRequiredInterceptor extends EndpointInterceptor {

    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private SessionHelper sessionHelper;
    @Inject private InternalHttpRequestValidator internalHttpRequestValidator;
    @Inject private Logger logger;

    @Override
    protected boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        AuthenticationRequired authenticationRequired = getAuthenticationRequired(method).orElse(null);
        if (authenticationRequired == null) {
            return true;
        }

        Set<AuthenticationLevel> designatedLevels = new HashSet<>(Arrays.asList(authenticationRequired.levels()));
        boolean allowRequest = designatedLevels.stream()
                .anyMatch(level -> meetsAuthenticationLevel(request, level));

        if (!allowRequest) {
            logger.error(String.format("User authentication is required for %s", request.getRequestURL().toString()));
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            throw new AuthenticationException("User authentication is required.");
        }

        return true;
    }

    private Optional<AuthenticationRequired> getAuthenticationRequired(HandlerMethod method) {
        return Optional.ofNullable(method.getMethodAnnotation(AuthenticationRequired.class))
                .or(() -> Optional.ofNullable(method.getMethod().getDeclaringClass().getAnnotation(AuthenticationRequired.class)));
    }

    private boolean meetsAuthenticationLevel(HttpServletRequest request, AuthenticationLevel level) {
        logger.debug(String.format("Determining authentication at level %s for %s", level.name(), request.getRequestURI()));
        switch (level) {
            case AUTHENTICATED:
                return isActivelyAuthenticated(request);
            case INTERNAL_SERVICE:
                return isInternalServiceAuthorized(request);
            case NONE:
                return true;
            default:
                return false;
        }
    }

    private boolean isActivelyAuthenticated(HttpServletRequest request) {
        return sessionRequestHelper.getSessionFromRequest(request)
                .map(session -> !sessionHelper.isExpired(session))
                .orElse(false);
    }

    private boolean isInternalServiceAuthorized(HttpServletRequest request) {
        try {
            internalHttpRequestValidator.validateIncomingRequest(request);
        } catch (UnauthorizedInternalRequestException e) {
            return false;
        }
        return true;
    }
}
