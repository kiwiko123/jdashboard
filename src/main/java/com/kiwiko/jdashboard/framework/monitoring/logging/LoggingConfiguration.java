package com.kiwiko.jdashboard.framework.monitoring.logging;

import com.kiwiko.jdashboard.webapp.framework.application.properties.PropertiesConfiguration;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.framework.monitoring.logging.impl.ConfigurationLogger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    @ConfiguredBy(PropertiesConfiguration.class)
    public Logger logger() {
        return new ConfigurationLogger();
    }
}
