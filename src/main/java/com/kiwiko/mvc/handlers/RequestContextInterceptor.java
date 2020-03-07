package com.kiwiko.mvc.handlers;

import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.api.RequestContextService;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;

public class RequestContextInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private RequestContextService requestContextService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Instant now = Instant.now();
        String requestUrl = requestContextService.getRequestUrl(request);
        RequestContext requestContext = new RequestContext(requestUrl, now, null);
        requestContextService.saveRequestContext(requestContext);

        HttpSession session = request.getSession();
        session.setAttribute(RequestContextService.REQUEST_CONTEXT_SESSION_KEY, requestContext);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        Instant now = Instant.now();
        String requestUrl = requestContextService.getRequestUrl(request);
        RequestContext requestContext = requestContextService.getRequestContext(request)
                .orElseThrow(() -> new RequestError(String.format("No RequestContext found after handling \"%s\"", requestUrl)));
        requestContext.setEndInstant(now);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(RequestContextService.REQUEST_CONTEXT_SESSION_KEY, requestContext);
        }
        requestContextService.saveRequestContext(requestContext);

        super.postHandle(request, response, handler, modelAndView);
    }
}
