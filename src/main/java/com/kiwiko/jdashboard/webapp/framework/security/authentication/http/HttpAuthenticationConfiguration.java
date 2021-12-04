package com.kiwiko.jdashboard.webapp.framework.security.authentication.http;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal.JdashboardInternalHttpRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public InternalHttpRequestValidator internalHttpRequestValidator() {
        return new JdashboardInternalHttpRequestValidator();
    }
}
