package com.kiwiko.webapp.mvc.requests.internal.interceptors;

import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestErrorInterceptor implements EndpointInterceptor {

    @Inject private Logger logger;

    @Override
    public void afterRequestCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, @Nullable Exception exception) throws Exception {
        if (exception == null) {
            return;
        }
        logger.error(String.format("Uncaught exception while handling request %s", request.getRequestURL().toString()), exception);
    }
}
