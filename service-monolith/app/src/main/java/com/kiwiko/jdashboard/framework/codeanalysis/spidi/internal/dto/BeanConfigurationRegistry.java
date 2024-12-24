package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A registry that maps {@link org.springframework.context.annotation.Bean} classes to the
 * {@link org.springframework.context.annotation.Configuration} that wires them.
 */
public class BeanConfigurationRegistry {

    private final Map<Class<?>, Class<?>> registry;

    public BeanConfigurationRegistry() {
        registry = new HashMap<>();
    }

    public Optional<Class<?>> getConfigurationForBean(Class<?> beanClass) {
        return Optional.ofNullable(registry.get(beanClass));
    }

    public void register(Class<?> beanClass, Class<?> configurationClass) {
        registry.put(beanClass, configurationClass);
    }

    public Map<Class<?>, Class<?>> all() {
        return Map.copyOf(registry);
    }

    @Override
    public String toString() {
        return "BeanConfigurationRegistry{" +
                "registry=" + registry +
                '}';
    }
}
