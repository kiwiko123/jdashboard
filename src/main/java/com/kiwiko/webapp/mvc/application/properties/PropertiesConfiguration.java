package com.kiwiko.webapp.mvc.application.properties;

import com.kiwiko.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyMapper;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.webapp.mvc.application.properties.internal.JdashboardPropertyFileIdentifier;
import com.kiwiko.webapp.mvc.application.properties.internal.JdashboardPropertyFileNormalizer;
import com.kiwiko.webapp.mvc.application.properties.internal.MemoryManageableJdashboardPropertyFileReader;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.webapp.mvc.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PropertiesConfiguration.class)
public class PropertiesConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardPropertyReader jdashboardPropertyFileReader() {
        return new MemoryManageableJdashboardPropertyFileReader();
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
