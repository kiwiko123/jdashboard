package com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalHttpAuthenticationConfiguration {

    @Bean
    public JdashboardInternalRequestHasher jdashboardInternalRequestHasher() {
        return new JdashboardInternalRequestHasher();
    }
}
