package com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl;

import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.ConfiguredBean;
import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto.DependencyMetadata;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;
import com.kiwiko.jdashboard.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyInjectedConfigurationResolver implements ApplicationStartupHook {

    @Inject private ClassScanner classScanner;
    @Inject private DependencyResolver dependencyResolver;
    @Inject private Logger logger;

    @Override
    public void run() {
        ConfigurationRegistry configurationRegistry = buildConfigurationRegistry();
        BeanConfigurationRegistry beanConfigurationRegistry = buildBeanConfigurationRegistry(configurationRegistry);
        BeanDependencyRegistry beanDependencyRegistry = buildBeanDependencyRegistry(beanConfigurationRegistry);

        dependencyResolver.resolveDependencies(configurationRegistry, beanConfigurationRegistry, beanDependencyRegistry);
    }

    private Set<Class<?>> getDependencyInjectingClasses() {
        return classScanner.getClasses("com.kiwiko.jdashboard").stream()
                .filter(this::classInjectsDependencies)
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean classInjectsDependencies(Class<?> cls) {
        if (DependencyResolverConstants.IGNORED_DEPENDENCY_INJECTING_CLASSES.contains(cls.getName())) {
            return false;
        }

        // Only concrete classes can inject dependencies.
        if (cls.isInterface()) {
            return false;
        }

        if (cls.isInstance(JdashboardDependencyConfiguration.class)) {
            return false;
        }

        if (cls.getAnnotation(org.springframework.context.annotation.Configuration.class) != null) {
            return false;
        }

        // Currently we have no clear setup to link a configuration with a controller, so exclude them for now.
        if (cls.getDeclaredAnnotation(Controller.class) != null || cls.getDeclaredAnnotation(RestController.class) != null) {
            return false;
        }

        return Arrays.stream(cls.getDeclaredFields())
                .map(field -> field.getAnnotation(Inject.class))
                .anyMatch(Objects::nonNull);
    }

    /**
     * @param configurationRegistry the result of {@link #buildConfigurationRegistry()}
     * @return a registry that maps { beanClass: configurationClass }
     */
    private BeanConfigurationRegistry buildBeanConfigurationRegistry(ConfigurationRegistry configurationRegistry) {
        BeanConfigurationRegistry registry = new BeanConfigurationRegistry();
        List<Class<?>> configurationClasses = configurationRegistry.all().stream()
                .map(Configuration::getConfigurationClass)
                .sorted(Comparator.comparing(Class::getName)) // TODO: remove this; sorted for easier debugging.
                .collect(Collectors.toUnmodifiableList());

        for (Class<?> configurationClass : configurationClasses) {
            Arrays.stream(configurationClass.getDeclaredMethods())
                    .filter(method -> method.getDeclaredAnnotation(Bean.class) != null)
                    .forEach(method -> registry.register(method.getReturnType(), configurationClass));
        }

        return registry;
    }

    /**
     * @param beanConfigurationRegistry the result of {@link #buildBeanConfigurationRegistry(ConfigurationRegistry)}
     * @return a registry that maps { beanClass: dependencyMetadata }
     */
    private BeanDependencyRegistry buildBeanDependencyRegistry(BeanConfigurationRegistry beanConfigurationRegistry) {
        BeanDependencyRegistry registry = new BeanDependencyRegistry();
        Set<Class<?>> dependencyInjectingClasses = getDependencyInjectingClasses();

        for (Class<?> cls : dependencyInjectingClasses) {
            DependencyMetadata dependencyMetadata = new DependencyMetadata();

            Set<Class<?>> injectedClasses = Arrays.stream(cls.getDeclaredFields())
                    .filter(this::isValidFieldInjectedDependency)
                    .map(Field::getType)
                    .collect(Collectors.toSet());

            Class<?> inferredBeanType = dependencyResolver.getInferredBeanType(cls, beanConfigurationRegistry);
            beanConfigurationRegistry.getConfigurationForBean(inferredBeanType)
                    .ifPresent(dependencyMetadata::setConfigurationClass);
            dependencyMetadata.setInjectedClasses(injectedClasses);

            // TODO handle duplicates?
            registry.register(inferredBeanType, dependencyMetadata);
        }

        return registry;
    }

    /**
     * @return a registry of {configurationClass: configuration}
     */
    private ConfigurationRegistry buildConfigurationRegistry() {
        ConfigurationRegistry registry = new ConfigurationRegistry();
        Set<Class<?>> configurationClasses = classScanner.getClasses("com.kiwiko.jdashboard").stream()
                .filter(this::isConfigurationClass)
                .collect(Collectors.toUnmodifiableSet());

        for (Class<?> cls : configurationClasses) {
            Set<ConfiguredBean> configuredBeans = getConfiguredBeans(cls);
            Configuration configuration = new Configuration(cls, configuredBeans);
            registry.register(cls, configuration);
        }

        return registry;
    }

    private boolean isConfigurationClass(Class<?> cls) {
        return cls.isInstance(JdashboardDependencyConfiguration.class)
                || cls.getAnnotation(org.springframework.context.annotation.Configuration.class) != null;
    }

    private Set<ConfiguredBean> getConfiguredBeans(Class<?> configurationClass) {
        Set<ConfiguredBean> configuredBeans = new HashSet<>();

        for (Method method : configurationClass.getDeclaredMethods()) {
            Class<?> returnType = method.getReturnType();
            ConfiguredBy configuredBy = method.getDeclaredAnnotation(ConfiguredBy.class);

            configuredBeans.add(new ConfiguredBean(returnType, configuredBy));
        }

        return configuredBeans;
    }

    private boolean isValidFieldInjectedDependency(Field field) {
        if (field.getDeclaredAnnotation(Inject.class) == null) {
            return false;
        }

        return !DependencyResolverConstants.IGNORED_DEPENDENCY_CLASSES.contains(field.getType().getName());
    }
}
