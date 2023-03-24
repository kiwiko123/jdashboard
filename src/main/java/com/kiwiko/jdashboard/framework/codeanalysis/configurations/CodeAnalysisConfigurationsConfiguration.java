package com.kiwiko.jdashboard.framework.codeanalysis.configurations;

import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.DependencyInjectedConfigurationResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.DependencyResolver;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.reflection.ReflectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeAnalysisConfigurationsConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({LoggingConfiguration.class, ReflectionConfiguration.class})
    public DependencyInjectedConfigurationResolver dependencyInjectedConfigurationResolver() {
        return new DependencyInjectedConfigurationResolver();
    }

    @Bean
    @ConfiguredBy({LoggingConfiguration.class})
    public DependencyResolver dependencyResolverDeprecated() {
        return new DependencyResolver();
    }
}
