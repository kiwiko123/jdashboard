package com.kiwiko.jdashboard.services.userauth;

import com.kiwiko.jdashboard.services.userauth.internal.UserAuthenticator;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public UserAuthenticator userAuthenticator() {
        return new UserAuthenticator();
    }
}
