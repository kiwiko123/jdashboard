package com.kiwiko.jdashboard.webapp.mvc.di.internal;

import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.ConfigurationResolver;
import com.kiwiko.jdashboard.webapp.mvc.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.jdashboard.webapp.mvc.di.api.interfaces.exceptions.DependencyInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.inject.Inject;
import java.util.Set;

public class SpringAnnotationConfigApplicationContextDependencyInstantiator implements DependencyInstantiator {

    @Inject private ConfigurationResolver configurationResolver;

    @Override
    public <T> T instantiateDependency(Class<T> clazz, Class<?> configurationType) throws DependencyInstantiationException {
        Set<Class<?>> configurationTypes = configurationResolver.getConfigurations(configurationType);
        Class<?>[] configurationTypesArray = new Class[configurationTypes.size()];
        configurationTypes.toArray(configurationTypesArray);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(configurationTypesArray);

        try {
            return applicationContext.getBean(clazz);
        } catch (BeansException e) {
            throw new DependencyInstantiationException(String.format("Error instantiating type %s", clazz.getName()), e);
        }
    }
}
