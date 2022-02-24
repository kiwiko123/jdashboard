package com.kiwiko.jdashboard.framework.lifecycle.startup;

import com.kiwiko.jdashboard.framework.lifecycle.startup.internal.ApplicationStartupService;
import com.kiwiko.jdashboard.framework.lifecycle.startup.registry.ApplicationStartupChain;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationStartupConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ApplicationStartupService applicationStartupService() {
        return new ApplicationStartupService();
    }

    @Bean
    public ApplicationStartupChain applicationStartupChain() {
        return new ApplicationStartupChain();
    }
}
