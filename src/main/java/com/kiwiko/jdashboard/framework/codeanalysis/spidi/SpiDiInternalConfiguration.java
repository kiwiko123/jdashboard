package com.kiwiko.jdashboard.framework.codeanalysis.spidi;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyConfigurationAnalyzer;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyInjectionInspector;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.SpiDiServiceImpl;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.ConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PackageDependencyInjectionConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PackageTransitiveConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PrivateDependencyInjectionConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PrivateTransitiveConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PublicDependencyInjectionConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope.PublicTransitiveConfigurationScopeResolver;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.reflection.ReflectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class SpiDiInternalConfiguration implements JdashboardDependencyConfiguration {

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
    public PrivateDependencyInjectionConfigurationScopeResolver privateConfigurationScopeLevelResolver() {
        return new PrivateDependencyInjectionConfigurationScopeResolver();
    }

    @Bean
    public PackageDependencyInjectionConfigurationScopeResolver packageConfigurationScopeLevelResolver() {
        return new PackageDependencyInjectionConfigurationScopeResolver();
    }

    @Bean
    public PublicDependencyInjectionConfigurationScopeResolver publicConfigurationScopeLevelResolver() {
        return new PublicDependencyInjectionConfigurationScopeResolver();
    }

    @Bean
    public PrivateTransitiveConfigurationScopeResolver privateTransitiveConfigurationScopeResolver() {
        return new PrivateTransitiveConfigurationScopeResolver();
    }

    @Bean
    public PackageTransitiveConfigurationScopeResolver packageTransitiveConfigurationScopeResolver() {
        return new PackageTransitiveConfigurationScopeResolver();
    }

    @Bean
    public PublicTransitiveConfigurationScopeResolver publicTransitiveConfigurationScopeResolver() {
        return new PublicTransitiveConfigurationScopeResolver();
    }
}
