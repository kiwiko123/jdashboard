package com.kiwiko.mvc.lifecycle.dependencies.manual.data;

import com.kiwiko.lang.reflection.ReflectionHelper;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.internal.ConsoleLogService;
import com.kiwiko.mvc.lifecycle.dependencies.data.DependencyBinding;
import com.kiwiko.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import com.kiwiko.mvc.lifecycle.startup.api.errors.LifecycleException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class InjectManuallyConfigurer<T> {

    private T instance;
    private final Set<DependencyBinding> dependencyBindings;
    private ReflectionHelper reflectionHelper;
    private final LogService logService;

    public InjectManuallyConfigurer() {
        instance = null;
        dependencyBindings = new HashSet<>();
        reflectionHelper = new ReflectionHelper();
        logService = new ConsoleLogService();
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public void addDependencyBinding(DependencyBinding binding) {
        dependencyBindings.add(binding);
    }

    public T create() {
        reflectionHelper.getFields(instance.getClass()).stream()
                .filter(field -> field.getDeclaredAnnotation(InjectManually.class) != null)
                .filter(field -> supportsDependencyBinding(field.getType()))
                .forEach(this::injectInstance);
        return instance;
    }

    private boolean supportsDependencyBinding(Class<?> type) {
        return dependencyBindings.stream()
                .map(DependencyBinding::getDependencyType)
                .anyMatch(dependencyType -> dependencyType == type);
    }

    private void injectInstance(Field field) {
        Class<?> bindingType = dependencyBindings.stream()
                .filter(dependencyBinding -> dependencyBinding.getDependencyType() == field.getType())
                .findFirst()
                .map(DependencyBinding::getBindingType)
                .orElseThrow(() -> new LifecycleException("No dependency binding type found"));

        Object dependencyInstance;
        try {
            dependencyInstance = bindingType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logService.error(String.format("Error creating instance of type %s for field %s", bindingType.toString(), field.toString()), e);
            return;
        }

        boolean isAccessible = field.trySetAccessible();
        if (!isAccessible) {
            logService.error(String.format("Failed to make field %s accessible", field.toString()));
            return;
        }

        try {
            field.set(instance, dependencyInstance);
        } catch (IllegalAccessException e) {
            logService.error(String.format("Failed to make field %s accessible", field.toString()), e);
        }
    }
}
