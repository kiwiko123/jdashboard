package com.kiwiko.webapp.mvc.requests.internal.interceptors;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import com.kiwiko.webapp.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.webapp.mvc.requests.api.RequestContextService;
import com.kiwiko.webapp.mvc.requests.api.RequestError;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.sessions.data.Session;
import com.kiwiko.webapp.mvc.security.sessions.data.SessionProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

/**
 * Interceptor that creates a {@link com.kiwiko.webapp.mvc.requests.internal.dataAccess.RequestContextEntity} for every
 * web request that goes through Jdashboard.
 */
public class RequestContextInterceptor implements EndpointInterceptor {

    @Inject private RequestContextService requestContextService;
    @Inject private SessionRequestHelper sessionRequestHelper;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        Instant now = Instant.now();
        String requestUri = requestContextService.getRequestUri(request);

        RequestContext requestContext = new RequestContext();
        requestContext.setUri(requestUri);
        requestContext.setStartTime(now);
        sessionRequestHelper.getSessionFromRequest(request)
                .flatMap(Session::getUser)
                .ifPresent(requestContext::setUser);
        requestContext = requestContextService.saveRequestContext(requestContext);

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
        requestContextService.saveRequestContext(requestContext);

        Duration requestDuration = Duration.between(requestContext.getStartTime(), requestContext.getEndTime().orElse(now));
        logger.debug(String.format("(%d ms) %s", requestDuration.toMillis(), makeDebugRequestUri(request)));
    }

    private String makeDebugRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query == null) {
            return uri;
        }

        String result = uri;
        if (uri.endsWith("/")) {
            result = result.substring(0, result.length() - 1);
        }

        return String.format("%s?%s", result, query);
    }
}
