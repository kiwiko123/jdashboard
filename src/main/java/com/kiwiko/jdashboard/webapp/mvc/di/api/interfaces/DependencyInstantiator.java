package com.kiwiko.jdashboard.webapp.mvc.di.api.interfaces;

import com.kiwiko.jdashboard.webapp.mvc.di.api.interfaces.exceptions.DependencyInstantiationException;

public interface DependencyInstantiator {

    /**
     * Instantitate a Spring {@link org.springframework.context.annotation.Bean} by providing the bean's type and its
     * {@link org.springframework.context.annotation.Configuration} class.
     *
     * @param clazz the bean's type / the type to instantiate
     * @param configurationType the {@link org.springframework.context.annotation.Configuration} class that wires the bean
     * @return the instantiated bean
     * @throws DependencyInstantiationException if there was an error attempting to instantiate the bean
     */
    <T> T instantiateDependency(Class<T> clazz, Class<?> configurationType) throws DependencyInstantiationException;
}
