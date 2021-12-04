package com.kiwiko.jdashboard.webapp.framework.security.authentication;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events.UserAuthenticationEventClient;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events.DirectServiceUserAuthenticationEventClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthenticationConfiguration.class)
public class AuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public UserAuthenticationEventClient userAuthenticationEventClient() {
        return new DirectServiceUserAuthenticationEventClient();
    }


}
