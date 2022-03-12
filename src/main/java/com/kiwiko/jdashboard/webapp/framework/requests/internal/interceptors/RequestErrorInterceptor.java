package com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestErrorInterceptor implements RequestInterceptor {

    @Inject private Logger logger;

    @Override
    public void afterRequestCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, @Nullable Exception exception) throws Exception {
        if (exception == null) {
            return;
        }
        logger.error(String.format("Uncaught exception while handling request %s", request.getRequestURL().toString()), exception);
    }
}
