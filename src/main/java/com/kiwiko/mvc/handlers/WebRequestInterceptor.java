package com.kiwiko.mvc.handlers;

import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.api.RequestContextService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

public class WebRequestInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private RequestContextService requestContextService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = requestContextService.getRequestUrl(request);
        int currentCount = requestContextService.getRequestContext(requestUrl)
                .map(RequestContext::getCount)
                .orElse(0);
        RequestContext updatedContext = new RequestContext(currentCount + 1, Instant.now());
        requestContextService.saveRequestContext(requestUrl, updatedContext);

        return super.preHandle(request, response, handler);
    }
}
