package com.kiwiko.jdashboard.framework.codeanalysis.spidi;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyConfigurationAnalyzer;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyInjectionInspector;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.SpiDiServiceImpl;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.ConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PackageConfigurationScopeLevelResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PrivateConfigurationScopeLevelResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PublicConfigurationScopeLevelResolver;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.reflection.ReflectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiDiConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public SpiDiService spiDiService() {
        return new SpiDiServiceImpl();
    }

    @Bean
    @ConfiguredBy({
            ReflectionConfiguration.class,
            LoggingConfiguration.class,
    })
    public DependencyConfigurationAnalyzer dependencyConfigurationResolver() {
        return new DependencyConfigurationAnalyzer();
    }

    @Bean
    public DependencyInjectionInspector dependencyInjectionInspector() {
        return new DependencyInjectionInspector();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public DependencyResolver dependencyResolver() {
        return new DependencyResolver();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ConfigurationScopeResolver configurationScopeResolver() {
        return new ConfigurationScopeResolver();
    }

    @Bean
    public PrivateConfigurationScopeLevelResolver privateConfigurationScopeLevelResolver() {
        return new PrivateConfigurationScopeLevelResolver();
    }

    @Bean
    public PackageConfigurationScopeLevelResolver packageConfigurationScopeLevelResolver() {
        return new PackageConfigurationScopeLevelResolver();
    }

    @Bean
    public PublicConfigurationScopeLevelResolver publicConfigurationScopeLevelResolver() {
        return new PublicConfigurationScopeLevelResolver();
    }
}
