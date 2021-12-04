package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalHttpAuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public JdashboardInternalRequestHasher jdashboardInternalRequestHasher() {
        return new JdashboardInternalRequestHasher();
    }
}
