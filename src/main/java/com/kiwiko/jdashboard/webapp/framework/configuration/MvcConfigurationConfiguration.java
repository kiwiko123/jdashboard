package com.kiwiko.jdashboard.webapp.framework.configuration;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.ConfigurationResolver;
import com.kiwiko.jdashboard.webapp.framework.configuration.internal.ConfiguredByConfigurationResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MvcConfigurationConfiguration.class)
public class MvcConfigurationConfiguration {

    @Bean
    public ConfigurationResolver configurationResolver() {
        return new ConfiguredByConfigurationResolver();
    }
}
