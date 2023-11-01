package com.kiwiko.jdashboard.framework.ratelimiting.interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitInterceptorConfiguration {

    @Bean
    public RateLimiterInterceptor rateLimiterInterceptor() {
        return new RateLimiterInterceptor();
    }

    @Bean
    public RateLimitRequestService rateLimitRequestService() {
        return new RateLimitRequestService();
    }
}
