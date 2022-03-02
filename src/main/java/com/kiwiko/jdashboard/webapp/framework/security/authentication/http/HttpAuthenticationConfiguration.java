package com.kiwiko.jdashboard.webapp.framework.security.authentication.http;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.services.sessions.SessionConfiguration;
import com.kiwiko.jdashboard.webapp.application.events.ApplicationEventConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal.InternalHttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal.JdashboardInternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({
            SessionConfiguration.class,
            LoggingConfiguration.class,
            InternalHttpAuthenticationConfiguration.class,
            ApplicationEventConfiguration.class,
            UniversalUniqueIdentifierConfiguration.class
    })
    public InternalHttpRequestValidator internalHttpRequestValidator() {
        return new JdashboardInternalHttpRequestValidator();
    }
}
