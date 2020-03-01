package com.kiwiko.mvc.handlers;

import com.kiwiko.mvc.metrics.api.LogService;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RequestErrorInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private LogService logService;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        Optional.ofNullable(ex)
                .ifPresent(exception ->
                        logService.error(String.format("Uncaught exception while handling request %s", request.getRequestURI()), exception));
    }
}
