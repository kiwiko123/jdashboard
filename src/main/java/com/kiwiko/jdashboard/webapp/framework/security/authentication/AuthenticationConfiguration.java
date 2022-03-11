package com.kiwiko.jdashboard.webapp.framework.security.authentication;

import com.kiwiko.jdashboard.services.sessions.SessionConfiguration;
import com.kiwiko.jdashboard.webapp.application.events.ApplicationEventConfiguration;
import com.kiwiko.jdashboard.webapp.framework.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events.UserAuthenticationEventClient;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events.DirectServiceUserAuthenticationEventClient;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.InternalServiceCheckInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.UserAuthCheckInterceptor;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthenticationConfiguration.class)
public class AuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(ApplicationEventConfiguration.class)
    public UserAuthenticationEventClient userAuthenticationEventClient() {
        return new DirectServiceUserAuthenticationEventClient();
    }

    @Bean
    @ConfiguredBy({MvcConfiguration.class, SessionConfiguration.class})
    public UserAuthCheckInterceptor userAuthCheckInterceptor() {
        return new UserAuthCheckInterceptor();
    }

    @Bean
    @ConfiguredBy({ LoggingConfiguration.class, HttpAuthenticationConfiguration.class })
    public InternalServiceCheckInterceptor internalServiceCheckInterceptor() {
        return new InternalServiceCheckInterceptor();
    }
}
