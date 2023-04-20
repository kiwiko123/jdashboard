package com.kiwiko.jdashboard.framework.interceptors;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.interceptors.ApplicationRequestLogInterceptorConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.AuthenticationConfiguration;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptorChain;
import com.kiwiko.jdashboard.framework.interceptors.internal.RequestInterceptorExecutor;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.requests.RequestConfiguration;
import com.kiwiko.jdashboard.framework.security.csrf.CsrfConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptorExecutor());
    }

    @Bean
    @ConfiguredBy({
            CsrfConfiguration.class,
            MvcConfiguration.class,
            ApplicationRequestLogInterceptorConfiguration.class,
            RequestConfiguration.class,
            AuthenticationConfiguration.class
    })
    public RequestInterceptorChain requestInterceptorChain() {
        return new RequestInterceptorChain();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public RequestInterceptorExecutor requestInterceptorExecutor() {
        return new RequestInterceptorExecutor();
    }
}
