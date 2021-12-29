package com.kiwiko.jdashboard.webapp.framework.configuration.internal;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.ConfigurationResolver;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.exceptions.ConfigurationResolvingException;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

public class ConfiguredByConfigurationResolver implements ConfigurationResolver {

    @Override
    public Set<Class<?>> getConfigurations(Class<?> clazz) throws ConfigurationResolvingException {
        Set<Class<?>> configurationClasses = new HashSet<>();

        if (clazz.getDeclaredAnnotation(Configuration.class) == null) {
            throw new ConfigurationResolvingException(String.format("Class %s is not a Spring @Configuration", clazz.getSimpleName()));
        }

        configurationClasses.add(clazz);

        ConfiguredBy configuredBy = clazz.getDeclaredAnnotation(ConfiguredBy.class);
        if (configuredBy != null) {
            for (Class<?> configurationDependency : configuredBy.value()) {
                Set<Class<?>> transitiveConfigurations = getConfigurations(configurationDependency);
                configurationClasses.addAll(transitiveConfigurations);
            }
        }

        return configurationClasses;
    }
}
