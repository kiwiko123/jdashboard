package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class Configuration {

    private final Class<?> configurationClass;
    private final Set<ConfiguredBean> configuredBeans;

    public Configuration(Class<?> configurationClass, Collection<ConfiguredBean> configuredBeans) {
        this.configurationClass = configurationClass;
        this.configuredBeans = Set.copyOf(configuredBeans);
    }

    public Configuration(Class<?> configurationClass) {
        this(configurationClass, Collections.emptySet());
    }

    public Class<?> getConfigurationClass() {
        return configurationClass;
    }

    public Set<ConfiguredBean> getConfiguredBeans() {
        return configuredBeans;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "configurationClass=" + configurationClass +
                ", configuredBeans=" + configuredBeans +
                '}';
    }
}
