package com.kiwiko.webapp.mvc.application.properties;

import com.kiwiko.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.webapp.mvc.application.properties.internal.JdashboardPropertyFileIdentifier;
import com.kiwiko.webapp.mvc.application.properties.internal.JdashboardPropertyFileParser;
import com.kiwiko.webapp.mvc.application.properties.internal.JdashboardPropertyFileReader;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.webapp.mvc.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardPropertyReader jdashboardPropertyFileReader() {
        return new JdashboardPropertyFileReader();
    }

    @Bean
    public JdashboardPropertyFileParser jdashboardPropertyFileParser() {
        return new JdashboardPropertyFileParser();
    }

    @Bean
    @ConfiguredBy(EnvironmentConfiguration.class)
    public JdashboardPropertyFileIdentifier jdashboardPropertyFileIdentifier() {
        return new JdashboardPropertyFileIdentifier();
    }
}
