package com.kiwiko.jdashboard.webapp.middleware.interceptors.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import com.kiwiko.jdashboard.webapp.middleware.interceptors.api.interfaces.EndpointInterceptorChain;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

public class EndpointInterceptorExecutor extends HandlerInterceptorAdapter {

    @Inject private EndpointInterceptorChain endpointInterceptorChain;
    @Inject private Logger logger;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            logger.debug(String.format("%s has unknown handler type %s for url %s; denying request", getClass().getName(), handler.getClass().getName(), request.getRequestURL().toString()));
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean allowRequest = true;
        Iterator<EndpointInterceptor> interceptorIterator = endpointInterceptorChain.getInterceptors().iterator();

        while (allowRequest && interceptorIterator.hasNext()) {
            EndpointInterceptor interceptor = interceptorIterator.next();
            allowRequest = allowInterceptorRequest(interceptor, request, response, handlerMethod);
            if (!allowRequest) {
                logger.debug(String.format("%s denied request %s", interceptor.getClass().getSimpleName(), request.getRequestURL().toString()));
            }
        }

        return allowRequest;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        for (EndpointInterceptor interceptor : endpointInterceptorChain.getInterceptors()) {
            postHandleInterceptor(interceptor, request, response, handlerMethod, modelAndView);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        for (EndpointInterceptor interceptor : endpointInterceptorChain.getInterceptors()) {
            postCompleteInterceptor(interceptor, request, response, handlerMethod, ex);
        }
    }

    private boolean allowInterceptorRequest(
            EndpointInterceptor interceptor,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler) {
        try {
            return interceptor.allowRequest(request, response, handler);
        } catch (Exception e) {
            logger.error(String.format("Error evaluating interceptor %s on url %s", interceptor.getClass().getName(), request.getRequestURL().toString()), e);
        }
        return false;
    }

    private void postHandleInterceptor(
            EndpointInterceptor interceptor,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler,
            ModelAndView modelAndView) {
        try {
            interceptor.preRender(request, response, handler, modelAndView);
        } catch (Exception e) {
            logger.error(String.format("Error evaluating post-request interceptor %s on url %s", interceptor.getClass().getName(), request.getRequestURL().toString()), e);
        }
    }

    private void postCompleteInterceptor(
            EndpointInterceptor interceptor,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler,
            @Nullable Exception exception) {
        try {
            interceptor.afterRequestCompletion(request, response, handler, exception);
        } catch (Exception e) {
            logger.error(String.format("Error evaluating post-completion interceptor %s on url %s", interceptor.getClass().getName(), request.getRequestURL().toString()), e);
        }
    }
}
