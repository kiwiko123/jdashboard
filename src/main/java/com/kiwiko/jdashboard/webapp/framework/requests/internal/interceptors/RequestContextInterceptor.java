package com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors;

import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import com.kiwiko.jdashboard.webapp.framework.interceptors.internal.SessionRequestHelper;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestError;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.clients.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

/**
 * Interceptor that creates a {@link com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess.RequestContextEntity} for every
 * web request that goes through Jdashboard.
 */
public class RequestContextInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextInterceptor.class);

    @Inject private RequestContextService requestContextService;
    @Inject private SessionRequestHelper sessionRequestHelper;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        Instant now = Instant.now();
        String requestUri = requestContextService.getRequestUri(request);

        RequestContext requestContext = new RequestContext();
        requestContext.setUri(requestUri);
        requestContext.setStartTime(now);
        sessionRequestHelper.getSessionFromRequest(request)
                .map(Session::getUserId)
                .ifPresent(requestContext::setUserId);
        requestContext = requestContextService.create(requestContext);

        HttpSession session = request.getSession();
        session.setAttribute(SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY, requestContext.getId());

        return true;
    }

    @Override
    public void preRender(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, ModelAndView modelAndView) throws Exception {
        Instant now = Instant.now();
        String requestUri = requestContextService.getRequestUri(request);
        HttpSession session = request.getSession();
        RequestContext requestContext = requestContextService.getFromSession(session, SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .orElseThrow(() -> new RequestError(String.format("No RequestContext found after handling \"%s\"", requestUri)));
        requestContext.setEndTime(now);
        requestContext.setIsRemoved(true);
        requestContextService.merge(requestContext);

        Duration requestDuration = Duration.between(requestContext.getStartTime(), requestContext.getEndTime().orElse(now));
        LOGGER.debug("({} ms) {}", requestDuration.toMillis(), makeDebugRequestUri(request));
    }

    private String makeDebugRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query != null) {
            if (uri.endsWith("/")) {
                uri = uri.substring(0, uri.length() - 1);
            }
            uri = String.format("%s?%s", uri, query);
        }

        return String.format("(%s) %s", request.getMethod(), uri);
    }
}
