package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ConfigurationRegistry {

    private Map<Class<?>, Configuration> configurationRegistry;

    public ConfigurationRegistry() {
        this.configurationRegistry = new HashMap<>();
    }

    public void register(Class<?> configurationClass, Configuration configuration) {
        configurationRegistry.put(configurationClass, configuration);
    }

    public Optional<Configuration> getConfiguration(Class<?> configurationClass) {
        return Optional.ofNullable(configurationRegistry.get(configurationClass));
    }

    public Set<Configuration> all() {
        return Set.copyOf(configurationRegistry.values());
    }
}
