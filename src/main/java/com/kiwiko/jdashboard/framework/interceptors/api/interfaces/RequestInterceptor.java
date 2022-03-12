package com.kiwiko.jdashboard.framework.interceptors.api.interfaces;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestInterceptor {

    /**
     * Determine if the incoming request should be processed.
     * Returning false or throwing an exception will immediately halt the request.
     *
     * @param request
     * @param response
     * @param method
     * @return true if the request should proceed, or false if not
     * @throws Exception
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    default boolean allowRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method) throws Exception {
        return true;
    }

    /**
     * Invoked after the request has been handled, but before the view is rendered.
     * This method will not run if {@link #allowRequest(HttpServletRequest, HttpServletResponse, HandlerMethod)} returned false or threw an exception.
     *
     * @param request
     * @param response
     * @param method
     * @param modelAndView
     * @throws Exception
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView) 
     */
    default void preRender(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method,
            ModelAndView modelAndView) throws Exception { }

    /**
     * Invoked after the request has completed.
     * This method will not run if {@link #allowRequest(HttpServletRequest, HttpServletResponse, HandlerMethod)} returned false or threw an exception.
     * This method is guaranteed to run if {@link #allowRequest(HttpServletRequest, HttpServletResponse, HandlerMethod)} returned true,
     * regardless of whether an uncaught exception occurred in the request.
     * 
     * @param request
     * @param response
     * @param method
     * @param exception
     * @throws Exception
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(HttpServletRequest, HttpServletResponse, Object, Exception) 
     */
    default void afterRequestCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod method,
            @Nullable Exception exception) throws Exception { }
}
