package com.kiwiko.jdashboard.webapp.mvc.application.properties;

import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyMapper;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.internal.JdashboardPropertyFileIdentifier;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.internal.JdashboardPropertyFileNormalizer;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.internal.InMemoryJdashboardPropertyFileReader;
import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.mvc.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PropertiesConfiguration.class)
public class PropertiesConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardPropertyReader jdashboardPropertyFileReader() {
        return new InMemoryJdashboardPropertyFileReader();
    }

    @Bean
    public JdashboardPropertyFileNormalizer jdashboardPropertyFileParser() {
        return new JdashboardPropertyFileNormalizer();
    }

    @Bean
    @ConfiguredBy(EnvironmentConfiguration.class)
    public JdashboardPropertyFileIdentifier jdashboardPropertyFileIdentifier() {
        return new JdashboardPropertyFileIdentifier();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardPropertyMapper jdashboardPropertyMapper() {
        return new JdashboardPropertyMapper();
    }
}