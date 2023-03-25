package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.scope;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeViolationException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyConfigurationAnalyzer;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.DependencyMetadata;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.util.Map;

public class ConfigurationScopeResolver {

    @Inject private DependencyConfigurationAnalyzer dependencyConfigurationAnalyzer;
    @Inject private PackageConfigurationScopeLevelResolver packageConfigurationScopeLevelResolver;
    @Inject private PrivateConfigurationScopeLevelResolver privateConfigurationScopeLevelResolver;
    @Inject private PublicConfigurationScopeLevelResolver publicConfigurationScopeLevelResolver;
    @Inject private Logger logger;

    public void resolve() throws ConfigurationScopeViolationException {
        ConfigurationRegistry configurationRegistry = dependencyConfigurationAnalyzer.buildConfigurationRegistry();
        BeanConfigurationRegistry beanConfigurationRegistry = dependencyConfigurationAnalyzer.buildBeanConfigurationRegistry(configurationRegistry);
        BeanDependencyRegistry beanDependencyRegistry = dependencyConfigurationAnalyzer.buildBeanDependencyRegistry(beanConfigurationRegistry);

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

                resolveScopeLevel(resolveScopeLevelInput, configurationScope);
            }
        }
    }

    private void resolveScopeLevel(ResolveScopeLevelInput input, ConfigurationScope configurationScope) throws ConfigurationScopeViolationException {
        ConfigurationScopeLevel scopeLevel = configurationScope.value();
        switch (scopeLevel) {
            case PRIVATE -> privateConfigurationScopeLevelResolver.resolve(input);
            case PACKAGE -> packageConfigurationScopeLevelResolver.resolve(input);
            case PUBLIC -> publicConfigurationScopeLevelResolver.resolve(input);
            default -> logger.error("Unknown scope level {}", scopeLevel);
        }
    }
}
