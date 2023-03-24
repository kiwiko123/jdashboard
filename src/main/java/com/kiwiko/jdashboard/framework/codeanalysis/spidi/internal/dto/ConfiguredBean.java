package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;

import javax.annotation.Nullable;
import java.util.Optional;

public class ConfiguredBean {

    private Class<?> beanClass;
    private @Nullable ConfiguredBy configuredBy;

    public ConfiguredBean(Class<?> beanClass, @Nullable ConfiguredBy configuredBy) {
        this.beanClass = beanClass;
        this.configuredBy = configuredBy;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Optional<ConfiguredBy> getConfiguredBy() {
        return Optional.ofNullable(configuredBy);
    }

    public void setConfiguredBy(@Nullable ConfiguredBy configuredBy) {
        this.configuredBy = configuredBy;
    }

    @Override
    public String toString() {
        return "ConfiguredBean{" +
                "beanClass=" + beanClass +
                ", configuredBy=" + configuredBy +
                '}';
    }
}
