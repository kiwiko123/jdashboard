package com.kiwiko.jdashboard.framework.ratelimiting.interceptors;

import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RateLimiterInterceptor implements RequestInterceptor {
    @Inject private RateLimitRequestService rateLimitRequestService;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        return true;
//        boolean isRateLimited = rateLimitRequestService.isRateLimited(request);
//        if (isRateLimited) {
//            response.sendError(429); // Too Many Requests
//            return false;
//        }
//
//        return true;
    }
}
