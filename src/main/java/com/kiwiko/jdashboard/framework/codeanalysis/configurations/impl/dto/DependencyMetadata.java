package com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.dto;

import javax.annotation.Nullable;
import java.util.Set;

public class DependencyMetadata {
    private Set<Class<?>> injectedClasses;
    private @Nullable Class<?> configurationClass;

    public Set<Class<?>> getInjectedClasses() {
        return injectedClasses;
    }

    public void setInjectedClasses(Set<Class<?>> injectedClasses) {
        this.injectedClasses = injectedClasses;
    }

    @Nullable
    public Class<?> getConfigurationClass() {
        return configurationClass;
    }

    public void setConfigurationClass(@Nullable Class<?> configurationClass) {
        this.configurationClass = configurationClass;
    }

    @Override
    public String toString() {
        return "DependencyMetadata{" +
                "injectedClasses=" + injectedClasses +
                ", configurationClass=" + configurationClass +
                '}';
    }
}
