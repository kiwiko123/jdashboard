package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.Configuration;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfiguredBean;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.DependencyMetadata;
import com.kiwiko.jdashboard.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyConfigurationAnalyzer {

    @Inject private ClassScanner classScanner;
    @Inject private DependencyInjectionInspector dependencyInjectionInspector;
    @Inject private Logger logger;

    public void run() {
        ConfigurationRegistry configurationRegistry = buildConfigurationRegistry();
        BeanConfigurationRegistry beanConfigurationRegistry = buildBeanConfigurationRegistry(configurationRegistry);
        BeanDependencyRegistry beanDependencyRegistry = buildBeanDependencyRegistry(beanConfigurationRegistry);
    }

    private Set<Class<?>> getDependencyInjectingClasses() {
        return classScanner.getClasses("com.kiwiko.jdashboard").stream()
                .filter(dependencyInjectionInspector::classInjectsDependencies)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * @param configurationRegistry the result of {@link #buildConfigurationRegistry()}
     * @return a registry that maps { beanClass: configurationClass }
     */
    public BeanConfigurationRegistry buildBeanConfigurationRegistry(ConfigurationRegistry configurationRegistry) {
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
    public BeanDependencyRegistry buildBeanDependencyRegistry(BeanConfigurationRegistry beanConfigurationRegistry) {
        BeanDependencyRegistry registry = new BeanDependencyRegistry();
        Set<Class<?>> dependencyInjectingClasses = getDependencyInjectingClasses();

        for (Class<?> cls : dependencyInjectingClasses) {
            DependencyMetadata dependencyMetadata = new DependencyMetadata();

            Class<?> inferredBeanType = getInferredBeanType(cls, beanConfigurationRegistry);
            beanConfigurationRegistry.getConfigurationForBean(inferredBeanType)
                    .ifPresent(dependencyMetadata::setConfigurationClass);

            Set<Class<?>> dependencyInjectedTypes = dependencyInjectionInspector.getAllInjectedDependencies(cls);
            dependencyMetadata.setInjectedClasses(dependencyInjectedTypes);

            // TODO handle duplicates?
            registry.register(inferredBeanType, dependencyMetadata);
        }

        return registry;
    }

    /**
     * @return a registry of {configurationClass: configuration}
     */
    public ConfigurationRegistry buildConfigurationRegistry() {
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
        return cls.getAnnotation(org.springframework.context.annotation.Configuration.class) != null;
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

    // ------

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
}
