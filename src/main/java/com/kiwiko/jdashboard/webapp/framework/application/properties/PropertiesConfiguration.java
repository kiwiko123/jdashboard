package com.kiwiko.jdashboard.webapp.framework.application.properties;

import com.kiwiko.jdashboard.framework.caching.CachingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.CacheableJdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.JdashboardPropertyLoader;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.JdashboardPropertyFileIdentifier;
import com.kiwiko.jdashboard.webapp.framework.application.properties.internal.JdashboardPropertyFileNormalizer;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PropertiesConfiguration.class)
public class PropertiesConfiguration {

    @Bean
    @ConfiguredBy(CachingConfiguration.class)
    public JdashboardPropertyReader jdashboardPropertyFileReader() {
        return new CacheableJdashboardPropertyReader();
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
    public JdashboardPropertyLoader jdashboardPropertyLoader() {
        return new JdashboardPropertyLoader();
    }
}
