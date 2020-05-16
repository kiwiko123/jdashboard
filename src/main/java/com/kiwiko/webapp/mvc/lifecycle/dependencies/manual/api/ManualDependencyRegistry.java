package com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api;

import com.kiwiko.webapp.mvc.lifecycle.dependencies.api.DependencyRegistry;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.data.DependencyBinding;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class ManualDependencyRegistry implements DependencyRegistry {

    private final Map<Class<?>, DependencyBinding> registry;

    public ManualDependencyRegistry() {
        registry = new HashMap<>();
    }

    public void register(DependencyBinding dependencyBinding) {
        registry.putIfAbsent(dependencyBinding.getDependencyType(), dependencyBinding);
    }

    public Optional<DependencyBinding> get(Class<?> dependencyType) {
        return Optional.ofNullable(registry.get(dependencyType));
    }

    public Collection<DependencyBinding> all() {
        return new HashSet<>(registry.values());
    }
}
