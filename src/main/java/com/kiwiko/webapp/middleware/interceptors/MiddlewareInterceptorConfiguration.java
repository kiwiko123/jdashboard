package com.kiwiko.webapp.middleware.interceptors;

import com.kiwiko.webapp.middleware.interceptors.api.interfaces.EndpointInterceptorChain;
import com.kiwiko.webapp.middleware.interceptors.internal.EndpointInterceptorExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MiddlewareInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(endpointInterceptorExecutor());
    }

    @Bean
    public EndpointInterceptorChain endpointInterceptorChain() {
        return new EndpointInterceptorChain();
    }

    @Bean
    public EndpointInterceptorExecutor endpointInterceptorExecutor() {
        return new EndpointInterceptorExecutor();
    }
}
