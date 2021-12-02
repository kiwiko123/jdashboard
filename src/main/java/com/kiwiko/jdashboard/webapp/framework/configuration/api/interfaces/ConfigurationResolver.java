package com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.exceptions.ConfigurationResolvingException;

import java.util.Set;

public interface ConfigurationResolver {

    Set<Class<?>> getConfigurations(Class<?> clazz) throws ConfigurationResolvingException;
}
