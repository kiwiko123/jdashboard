package com.kiwiko.mvc.lifecycle.dependencies.data;

import com.kiwiko.dataStructures.Pair;

import java.util.Objects;

public class DependencyBinding {

    private final Class<?> dependencyType;
    private final Class<?> bindingType;

    public DependencyBinding(Class<?> dependencyType, Class<?> bindingType) {
        this.dependencyType = dependencyType;
        this.bindingType = bindingType;
    }

    public Class<?> getDependencyType() {
        return dependencyType;
    }

    public Class<?> getBindingType() {
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
