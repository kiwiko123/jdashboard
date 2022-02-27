package com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registry that maps injected {@link org.springframework.context.annotation.Bean} types to the dependencies that they need.
 */
public class BeanDependencyRegistry {

    private final Map<Class<?>, DependencyMetadata> beanDependencyRegistry;

    public BeanDependencyRegistry() {
        beanDependencyRegistry = new HashMap<>();
    }

    public Optional<DependencyMetadata> get(Class<?> beanClass) {
        return Optional.ofNullable(beanDependencyRegistry.get(beanClass));
    }

    public void register(Class<?> beanClass, DependencyMetadata dependencyMetadata) {
        beanDependencyRegistry.put(beanClass, dependencyMetadata);
    }

    public Map<Class<?>, DependencyMetadata> all() {
        return Map.copyOf(beanDependencyRegistry);
    }

    @Override
    public String toString() {
        return "BeanDependencyRegistry{" +
                "beanDependencyRegistry=" + beanDependencyRegistry +
                '}';
    }
}
