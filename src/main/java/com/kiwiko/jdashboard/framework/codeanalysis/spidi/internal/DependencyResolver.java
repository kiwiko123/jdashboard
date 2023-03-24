package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.DependencyErrorActionLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.DependencyViolationException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ResolveDependenciesInput;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfiguredBean;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.DependencyMetadata;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DependencyResolver {

    @Inject private DependencyConfigurationAnalyzer dependencyConfigurationAnalyzer;
    @Inject private Logger logger;

    public void resolveDependencies(
            ResolveDependenciesInput resolveDependenciesInput,
            ConfigurationRegistry configurationRegistry,
            BeanConfigurationRegistry beanConfigurationRegistry,
            BeanDependencyRegistry beanDependencyRegistry) throws DependencyViolationException {
        for (Map.Entry<Class<?>, DependencyMetadata> entry : beanDependencyRegistry.all().entrySet()) {
            Class<?> beanClass = entry.getKey();
            DependencyMetadata dependencyMetadata = entry.getValue();
            resolveDependency(resolveDependenciesInput, beanClass, dependencyMetadata, configurationRegistry, beanConfigurationRegistry);
        }
    }

    private void resolveDependency(
            ResolveDependenciesInput resolveDependenciesInput,
            Class<?> beanClass,
            DependencyMetadata dependencyMetadata,
            ConfigurationRegistry configurationRegistry,
            BeanConfigurationRegistry beanConfigurationRegistry) throws DependencyViolationException {
        Class<?> inferredBeanClass = dependencyConfigurationAnalyzer.getInferredBeanType(beanClass, beanConfigurationRegistry);
        if (beanConfigurationRegistry.getConfigurationForBean(inferredBeanClass).isEmpty()) {
            processDependencyViolation(
                    resolveDependenciesInput.getDependencyErrorActionLevel(),
                    String.format("No configuration found for type %s", inferredBeanClass.getName()));
            return;
        }

        // Look up the @Configuration class that wires this bean.
        Configuration configuration = Optional.ofNullable(dependencyMetadata.getConfigurationClass())
                .flatMap(configurationRegistry::getConfiguration)
                .orElse(null);
        if (configuration == null) {
            processDependencyViolation(
                    resolveDependenciesInput.getDependencyErrorActionLevel(),
                    String.format("No configuration found for type %s", beanClass.getName()));
            return;
        }

        // Within this @Configuration, find the @Bean declaration that wires this bean.
        ConfiguredBean configuredBean = configuration.getConfiguredBeans().stream()
                .filter(bean -> bean.getBeanClass() == beanClass)
                .findFirst()
                .orElse(null);
        if (configuredBean == null) {
            processDependencyViolation(
                    resolveDependenciesInput.getDependencyErrorActionLevel(),
                    String.format("No @Bean found for type %s in configuration %s", beanClass.getSimpleName(), configuration.getConfigurationClass().getName()));
            return;
        }

        // Find the transitive dependencies for this bean by checking for a @ConfiguredBy annotation.
        Set<Class<?>> transitiveConfigurationClasses = configuredBean.getConfiguredBy()
                .map(ConfiguredBy::value)
                .map(values -> (Class<?>[]) values)
                .map(values -> new HashSet<>(Arrays.asList(values)))
                .orElseGet(HashSet::new);

        // The bean might inject something that its own configuration wires (e.g., something in the same package),
        // so let's consider its own configuration as well.
        transitiveConfigurationClasses.add(configuration.getConfigurationClass());

        // For each dependency that this bean injects,
        // find its corresponding configuration, and verify that it's present in the @ConfiguredBy annotation.
        for (Class<?> injectedClass : dependencyMetadata.getInjectedClasses()) {
            Class<?> injectedConfigurationType = beanConfigurationRegistry.getConfigurationForBean(injectedClass).orElse(null);
            if (injectedConfigurationType == null) {
                processDependencyViolation(
                        resolveDependenciesInput.getDependencyErrorActionLevel(),
                        String.format("%s injects bean %s but no transitive configuration was found", beanClass.getSimpleName(), injectedClass.getSimpleName()));
                return;
            }

            if (!transitiveConfigurationClasses.contains(injectedConfigurationType)) {
                processDependencyViolation(
                        resolveDependenciesInput.getDependencyErrorActionLevel(),
                        String.format(
                                "Bean %s in configuration %s is missing a transitive configuration for bean type %s; possibly %s",
                                beanClass.getSimpleName(),
                                configuration.getConfigurationClass().getSimpleName(),
                                injectedClass.getSimpleName(),
                                injectedConfigurationType.getSimpleName()));
            }
        }
    }

    private void processDependencyViolation(DependencyErrorActionLevel level, String message) throws DependencyViolationException {
        switch (level) {
            case WARN -> logger.warn(message);
            case RUNTIME_EXCEPTION -> throw new DependencyViolationException(message);
        }
    }
}
