package com.kiwiko.jdashboard.framework.lifecycle.startup;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.SpiDiExternalConfiguration;
import com.kiwiko.jdashboard.framework.lifecycle.startup.internal.ApplicationStartupService;
import com.kiwiko.jdashboard.framework.lifecycle.startup.registry.ApplicationStartupChain;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationStartupConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ApplicationStartupService applicationStartupService() {
        return new ApplicationStartupService();
    }

    @Bean
    @ConfiguredBy(SpiDiExternalConfiguration.class)
    public ApplicationStartupChain applicationStartupChain() {
        return new ApplicationStartupChain();
    }
}
