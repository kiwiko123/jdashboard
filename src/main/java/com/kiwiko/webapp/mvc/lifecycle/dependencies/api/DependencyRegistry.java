package com.kiwiko.webapp.mvc.lifecycle.dependencies.api;

import com.kiwiko.webapp.mvc.lifecycle.dependencies.data.DependencyBinding;

import java.util.Collection;
import java.util.Optional;

public interface DependencyRegistry {

    void register(DependencyBinding dependencyBinding);

    Optional<DependencyBinding> get(Class<?> dependencyType);

    Collection<DependencyBinding> all();
}
