package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyConfigurationAnalyzer;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfiguredBean;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.DependencyMetadata;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;

import javax.inject.Inject;
import java.util.Map;

public class ConfigurationScopeResolver {

    @Inject private DependencyConfigurationAnalyzer dependencyConfigurationAnalyzer;
    @Inject private PrivateDependencyInjectionConfigurationScopeResolver privateDependencyInjectionConfigurationScopeResolver;
    @Inject private PackageDependencyInjectionConfigurationScopeResolver packageDependencyInjectionConfigurationScopeResolver;
    @Inject private PublicDependencyInjectionConfigurationScopeResolver publicDependencyInjectionConfigurationScopeResolver;
    @Inject private PrivateTransitiveConfigurationScopeResolver privateTransitiveConfigurationScopeResolver;
    @Inject private PackageTransitiveConfigurationScopeResolver packageTransitiveConfigurationScopeResolver;
    @Inject private PublicTransitiveConfigurationScopeResolver publicTransitiveConfigurationScopeResolver;
    @Inject private Logger logger;

    public void resolve() throws ConfigurationScopeViolationException {
        ConfigurationRegistry configurationRegistry = dependencyConfigurationAnalyzer.buildConfigurationRegistry();
        BeanConfigurationRegistry beanConfigurationRegistry = dependencyConfigurationAnalyzer.buildBeanConfigurationRegistry(configurationRegistry);
        BeanDependencyRegistry beanDependencyRegistry = dependencyConfigurationAnalyzer.buildBeanDependencyRegistry(beanConfigurationRegistry);

        resolveConfigurationTransitiveDependencies(configurationRegistry);
        resolveInjectedDependencies(configurationRegistry, beanConfigurationRegistry, beanDependencyRegistry);
    }

    private void resolveConfigurationTransitiveDependencies(ConfigurationRegistry configurationRegistry) throws ConfigurationScopeViolationException {
        for (Configuration configuration : configurationRegistry.all()) {
            for (ConfiguredBean configuredBean : configuration.getConfiguredBeans()) {
                ConfiguredBy configuredBy = configuredBean.getConfiguredBy().orElse(null);
                if (configuredBy == null) {
                    continue;
                }

                for (Class<?> transitiveConfiguration : configuredBy.value()) {
                    ConfigurationScope configurationScope = transitiveConfiguration.getDeclaredAnnotation(ConfigurationScope.class);
                    if (configurationScope == null) {
                        continue;
                    }

                    ResolveTransitiveConfigurationScopeInput resolveTransitiveConfigurationScopeInput = ResolveTransitiveConfigurationScopeInput.builder()
                            .subjectConfigurationClass(configuration.getConfigurationClass())
                            .transitiveConfigurationClass(transitiveConfiguration)
                            .build();

                    resolveTransitiveConfigurationScopeLevel(resolveTransitiveConfigurationScopeInput, configurationScope);
                }
            }
        }
    }

    private void resolveTransitiveConfigurationScopeLevel(ResolveTransitiveConfigurationScopeInput input, ConfigurationScope configurationScope) throws ConfigurationScopeViolationException {
        ConfigurationScopeLevel scopeLevel = configurationScope.value();
        switch (scopeLevel) {
            case PRIVATE -> privateTransitiveConfigurationScopeResolver.resolve(input);
            case PACKAGE -> packageTransitiveConfigurationScopeResolver.resolve(input);
            case PUBLIC -> publicTransitiveConfigurationScopeResolver.resolve(input);
            default -> logger.error("Unknown scope level {}", scopeLevel);
        }
    }

    private void resolveInjectedDependencies(
            ConfigurationRegistry configurationRegistry,
            BeanConfigurationRegistry beanConfigurationRegistry,
            BeanDependencyRegistry beanDependencyRegistry) throws ConfigurationScopeViolationException {
        for (Map.Entry<Class<?>, DependencyMetadata> entry : beanDependencyRegistry.all().entrySet()) {
            Class<?> injectingClass = entry.getKey();
            DependencyMetadata dependencyMetadata = entry.getValue();

            for (Class<?> injectedDependency : dependencyMetadata.getInjectedClasses()) {
                Configuration configuration = beanConfigurationRegistry.getConfigurationForBean(injectedDependency)
                        .flatMap(configurationRegistry::getConfiguration)
                        .orElse(null);

                if (configuration == null) {
                    logger.info("No @Configuration found for injected dependency {}", injectedDependency);
                    continue;
                }

                ConfigurationScope configurationScope = configuration.getConfigurationClass().getDeclaredAnnotation(ConfigurationScope.class);
                if (configurationScope == null) {
                    continue;
                }

                ResolveScopeLevelInput resolveScopeLevelInput = ResolveScopeLevelInput.builder()
                        .injectingClass(injectingClass)
                        .injectedClass(injectedDependency)
                        .injectingConfigurationClass(configuration.getConfigurationClass())
                        .build();

                resolveDependencyInjectionScopeLevel(resolveScopeLevelInput, configurationScope);
            }
        }
    }

    private void resolveDependencyInjectionScopeLevel(ResolveScopeLevelInput input, ConfigurationScope configurationScope) throws ConfigurationScopeViolationException {
        ConfigurationScopeLevel scopeLevel = configurationScope.value();
        switch (scopeLevel) {
            case PRIVATE -> privateDependencyInjectionConfigurationScopeResolver.resolve(input);
            case PACKAGE -> packageDependencyInjectionConfigurationScopeResolver.resolve(input);
            case PUBLIC -> publicDependencyInjectionConfigurationScopeResolver.resolve(input);
            default -> logger.error("Unknown scope level {}", scopeLevel);
        }
    }
}
