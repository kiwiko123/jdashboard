package com.kiwiko.jdashboard.webapp.framework.requests.internal;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.requests.api.CurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.SessionProperties;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestAttributesCurrentRequestService implements CurrentRequestService {

    @Inject private Logger logger;
    @Inject private RequestContextService requestContextService;

    @Override
    public Optional<RequestContext> getCurrentRequestContext() {
        return getCurrentHttpServletRequest().map(HttpServletRequest::getSession)
                .flatMap(session -> requestContextService.getFromSession(session, SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY));
    }

    @Override
    public Optional<HttpServletRequest> getCurrentHttpServletRequest() {
        RequestAttributes requestAttributes = null;
        try {
            requestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            logger.error("Failed to obtain attributes from current request", e);
        }

        return Optional.ofNullable(requestAttributes)
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
}
