package com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors;

import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestErrorInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestErrorInterceptor.class);

    @Override
    public void afterRequestCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, @Nullable Exception exception) throws Exception {
        if (exception == null) {
            return;
        }
        LOGGER.error("Uncaught exception while handling request {}", request.getRequestURL().toString(), exception);
    }
}
