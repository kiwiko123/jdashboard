package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyInjectionInspector {
    private static final Map<Class<?>, Set<Class<?>>> CLASSES_TO_CONSTRUCTOR_INJECTED_CLASSES_CACHE = new HashMap<>();
    private static final Map<Class<?>, Set<Class<?>>> CLASSES_TO_FIELD_INJECTED_CLASSES_CACHE = new HashMap<>();

    public boolean classInjectsDependencies(Class<?> cls) {
        if (DependencyResolverConstants.IGNORED_DEPENDENCY_INJECTING_CLASSES.contains(cls.getName())) {
            return false;
        }

        // Only concrete classes can inject dependencies.
        if (cls.isInterface()) {
            return false;
        }

        if (cls.getAnnotation(org.springframework.context.annotation.Configuration.class) != null) {
            return false;
        }

        // Currently we have no clear setup to link a configuration with a controller, so exclude them for now.
        if (cls.getDeclaredAnnotation(Controller.class) != null || cls.getDeclaredAnnotation(RestController.class) != null) {
            return false;
        }

        return !getAllInjectedDependencies(cls).isEmpty();
    }

    public Set<Class<?>> getAllInjectedDependencies(Class<?> cls) {
        Set<Class<?>> constructorInjectedDependencies = getConstructorInjectedDependencies(cls);
        Set<Class<?>> fieldInjectedDependencies = getFieldInjectedDependencies(cls);

        return Sets.union(constructorInjectedDependencies, fieldInjectedDependencies);
    }

    public Set<Class<?>> getConstructorInjectedDependencies(Class<?> cls) {
        if (CLASSES_TO_CONSTRUCTOR_INJECTED_CLASSES_CACHE.containsKey(cls)) {
            return CLASSES_TO_CONSTRUCTOR_INJECTED_CLASSES_CACHE.get(cls);
        }

        Set<Class<?>> dependencies = Arrays.stream(cls.getDeclaredConstructors())
                .filter(constructor -> constructor.getDeclaredAnnotation(Inject.class) != null)
                // TODO ignore parameters with annotations
                .map(Constructor::getParameterTypes)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .filter(type -> !DependencyResolverConstants.IGNORED_DEPENDENCY_CLASSES.contains(type.getName()))
                .collect(Collectors.toUnmodifiableSet());

        CLASSES_TO_CONSTRUCTOR_INJECTED_CLASSES_CACHE.put(cls, dependencies);
        return dependencies;
    }

    public Set<Class<?>> getFieldInjectedDependencies(Class<?> cls) {
        if (CLASSES_TO_FIELD_INJECTED_CLASSES_CACHE.containsKey(cls)) {
            return CLASSES_TO_FIELD_INJECTED_CLASSES_CACHE.get(cls);
        }

        Set<Class<?>> dependencies = Arrays.stream(cls.getDeclaredFields())
                .filter(field -> field.getDeclaredAnnotation(Inject.class) != null)
                .map(Field::getType)
                .filter(type -> !DependencyResolverConstants.IGNORED_DEPENDENCY_CLASSES.contains(type.getName()))
                .collect(Collectors.toSet());

        CLASSES_TO_FIELD_INJECTED_CLASSES_CACHE.put(cls, dependencies);
        return dependencies;
    }
}
