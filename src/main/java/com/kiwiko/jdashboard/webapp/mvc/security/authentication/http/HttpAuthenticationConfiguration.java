package com.kiwiko.jdashboard.webapp.mvc.security.authentication.http;

import com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.internal.JdashboardInternalHttpRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAuthenticationConfiguration {

    @Bean
    public InternalHttpRequestValidator internalHttpRequestValidator() {
        return new JdashboardInternalHttpRequestValidator();
    }
}
