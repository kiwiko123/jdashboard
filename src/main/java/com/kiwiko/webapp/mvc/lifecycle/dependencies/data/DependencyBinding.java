package com.kiwiko.webapp.mvc.lifecycle.dependencies.data;

import java.util.Objects;
import java.util.Optional;

public class DependencyBinding<InterfaceType, ImplementationType extends InterfaceType> {

    private final Class<InterfaceType> dependencyType;
    private final Class<ImplementationType> bindingType;
    private final Optional<ImplementationType> bindingInstance;

    public DependencyBinding(Class<InterfaceType> dependencyType, Class<ImplementationType> bindingType) {
        this.dependencyType = dependencyType;
        this.bindingType = bindingType;
        bindingInstance = Optional.empty();
    }

    public DependencyBinding(Class<InterfaceType> dependencyType, ImplementationType bindingInstance) {
        this.dependencyType = dependencyType;
        bindingType = null;
        this.bindingInstance = Optional.of(bindingInstance);
    }

    public Class<InterfaceType> getDependencyType() {
        return dependencyType;
    }

    public Class<ImplementationType> getBindingType() {
        return bindingType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().isAssignableFrom(other.getClass())) {
            return false;
        }

        DependencyBinding otherBinding = (DependencyBinding) other;
        return dependencyType == otherBinding.dependencyType && bindingType == otherBinding.bindingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependencyType, bindingType);
    }
}
