package com.kiwiko.webapp.mvc.interceptors.api;

import com.kiwiko.library.metrics.api.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class EndpointInterceptor extends HandlerInterceptorAdapter {

    @Inject private Logger logger;

    protected boolean allowRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method) throws Exception {
        return true;
    }

    protected void afterRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method,
            ModelAndView modelAndView) throws Exception { }

    protected void afterRequestCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method,
            @Nullable Exception exception) throws Exception { }

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            logger.warn(String.format("%s has unknown handler type %s; denying request", getClass().getName(), handler.getClass().getName()));
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return allowRequest(request, response, handlerMethod);
    }

    @Override
    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        afterRequest(request, response, handlerMethod, modelAndView);
    }

    @Override
    public final void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        afterRequestCompletion(request, response, handlerMethod, ex);
    }
}
