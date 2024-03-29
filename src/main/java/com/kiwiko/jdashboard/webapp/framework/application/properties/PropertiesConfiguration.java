package com.kiwiko.jdashboard.webapp.framework.application.properties;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyMapper;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.JdashboardPropertyFileIdentifier;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.JdashboardPropertyFileNormalizer;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.InMemoryJdashboardPropertyFileReader;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PropertiesConfiguration.class)
public class PropertiesConfiguration implements JdashboardDependencyConfiguration {

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
