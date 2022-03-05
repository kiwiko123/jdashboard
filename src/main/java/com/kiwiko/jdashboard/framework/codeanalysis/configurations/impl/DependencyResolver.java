package com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl;

import com.kiwiko.jdashboard.framework.codeanalysis.configurations.api.interfaces.DependencyResolvingException;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.ConfiguredBean;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.DependencyMetadata;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DependencyResolver {

    @Inject private Logger logger;

    public Class<?> getInferredBeanType(Class<?> beanClass, BeanConfigurationRegistry beanConfigurationRegistry) {
        // If the configuration registry already wires up this bean, there's no work to be done.
        if (beanConfigurationRegistry.getConfigurationForBean(beanClass).isPresent()) {
            return beanClass;
        }

        for (Class<?> interfaceClass : beanClass.getInterfaces()) {
            Class<?> inferredBeanType = getInferredBeanType(interfaceClass, beanConfigurationRegistry);
            if (inferredBeanType != beanClass) {
                return inferredBeanType;
            }
        }

        // TODO currently this cannot identify controllers
//        logger.warn(String.format("Unable to infer bean type for %s", beanClass.getName()));
        return beanClass;
    }

    public void resolveDependencies(
            ConfigurationRegistry configurationRegistry,
            BeanConfigurationRegistry beanConfigurationRegistry,
            BeanDependencyRegistry beanDependencyRegistry) throws DependencyResolvingException {
        beanDependencyRegistry.all().forEach((beanClass, dependencyMetadata) -> {
            resolveDependency(beanClass, dependencyMetadata, configurationRegistry, beanConfigurationRegistry);
        });
    }

    private void resolveDependency(
            Class<?> beanClass,
            DependencyMetadata dependencyMetadata,
            ConfigurationRegistry configurationRegistry,
            BeanConfigurationRegistry beanConfigurationRegistry) throws DependencyResolvingException {
        Class<?> inferredBeanClass = getInferredBeanType(beanClass, beanConfigurationRegistry);
        if (beanConfigurationRegistry.getConfigurationForBean(inferredBeanClass).isEmpty()) {
            throwException(String.format("No configuration found for type %s", inferredBeanClass.getName()));
            return;
        }

        // Look up the @Configuration class that wires this bean.
        Configuration configuration = Optional.ofNullable(dependencyMetadata.getConfigurationClass())
                .flatMap(configurationRegistry::getConfiguration)
                .orElse(null);
        if (configuration == null) {
            throwException(String.format("No configuration found for type %s", beanClass.getName()));
            return;
        }

        // Within this @Configuration, find the @Bean declaration that wires this bean.
        ConfiguredBean configuredBean = configuration.getConfiguredBeans().stream()
                .filter(bean -> bean.getBeanClass() == beanClass)
                .findFirst()
                .orElse(null);
        if (configuredBean == null) {
            throwException(String.format("No @Bean found for type %s in configuration %s", beanClass.getSimpleName(), configuration.getConfigurationClass().getName()));
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
                throwException(String.format("%s injects bean %s but no transitive configuration was found", beanClass.getSimpleName(), injectedClass.getSimpleName()));
                return;
            }

            if (!transitiveConfigurationClasses.contains(injectedConfigurationType)) {
                throwException(
                        String.format(
                                "Bean %s in configuration %s is missing a transitive configuration for bean type %s; possibly %s",
                                beanClass.getSimpleName(),
                                configuration.getConfigurationClass().getSimpleName(),
                                injectedClass.getSimpleName(),
                                injectedConfigurationType.getSimpleName()));
            }
        }
    }

    private void throwException(String message) {
        logger.warn(message);
//        throw new DependencyResolvingException(message);
    }
}
