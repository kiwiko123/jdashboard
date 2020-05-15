package com.kiwiko.mvc.interceptors;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.interceptors.internal.SessionRequestHelper;
import com.kiwiko.mvc.requests.api.RequestContextService;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.security.sessions.data.Session;
import com.kiwiko.mvc.security.sessions.data.SessionProperties;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

public class RequestContextInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private RequestContextService requestContextService;

    @Inject
    private SessionRequestHelper sessionRequestHelper;

    @Inject
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        Instant now = Instant.now();
        String requestUri = requestContextService.getRequestUri(request);
        HttpSession session = request.getSession();
        RequestContext requestContext = requestContextService.getFromSession(session, SessionProperties.REQUEST_CONTEXT_ID_SESSION_KEY)
                .orElseThrow(() -> new RequestError(String.format("No RequestContext found after handling \"%s\"", requestUri)));
        requestContext.setEndTime(now);
        requestContextService.saveRequestContext(requestContext);

        Duration requestDuration = Duration.between(requestContext.getStartTime(), requestContext.getEndTime().orElse(now));
        logService.debug(String.format("(%d ms) %s", requestDuration.toMillis(), requestUri));

        super.postHandle(request, response, handler, modelAndView);
    }
}
