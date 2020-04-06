package com.kiwiko.mvc.interceptors;

import com.kiwiko.metrics.api.LogService;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestErrorInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private LogService logService;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
        if (ex != null) {
            String message = String.format("Uncaught exception while handling request %s", request.getRequestURI());
            logService.error(message, ex);
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
