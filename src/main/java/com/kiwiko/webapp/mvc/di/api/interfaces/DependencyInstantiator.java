package com.kiwiko.webapp.mvc.di.api.interfaces;

import com.kiwiko.webapp.mvc.di.api.interfaces.exceptions.DependencyInstantiationException;

public interface DependencyInstantiator {

    <T> T instantiateDependency(Class<T> clazz) throws DependencyInstantiationException;
}
