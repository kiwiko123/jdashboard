package com.kiwiko.jdashboard.webapp.middleware.interceptors;

import com.kiwiko.jdashboard.webapp.middleware.interceptors.api.interfaces.EndpointInterceptorChain;
import com.kiwiko.jdashboard.webapp.middleware.interceptors.internal.EndpointInterceptorExecutor;
import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.mvc.requests.RequestConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.security.csrf.CsrfConfiguration;
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
    @ConfiguredBy({
            LoggingConfiguration.class,
            CsrfConfiguration.class,
            MvcConfiguration.class,
            RequestConfiguration.class
    })
    public EndpointInterceptorChain endpointInterceptorChain() {
        return new EndpointInterceptorChain();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public EndpointInterceptorExecutor endpointInterceptorExecutor() {
        return new EndpointInterceptorExecutor();
    }
}
