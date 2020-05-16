package com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.data;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.data.DependencyBinding;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.errors.ManualInjectionException;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class InjectManuallyConfigurer<T> {

    private final ReflectionHelper reflectionHelper;
    private final Set<DependencyBinding> dependencyBindings;
    private T instance;


    public InjectManuallyConfigurer() {
        reflectionHelper = new ReflectionHelper();
        dependencyBindings = new HashSet<>();
        instance = null;
    }

    public InjectManuallyConfigurer<T> withBinding(Class<?> dependencyType, Class<?> bindingType) {
        DependencyBinding binding = new DependencyBinding(dependencyType, bindingType);
        return withBinding(binding);
    }

    public InjectManuallyConfigurer<T> withBinding(DependencyBinding binding) {
        dependencyBindings.add(binding);
        return this;
    }

    public InjectManuallyConfigurer<T> withInstance(T instance) {
        this.instance = instance;
        return this;
    }

    public T create() throws ManualInjectionException {
        if (instance == null) {
            throw new ManualInjectionException("No instance has been set");
        }

        reflectionHelper.getFields(instance.getClass()).stream()
                .filter(this::canInject)
                .filter(field -> supportsDependencyBinding(field.getType()))
                .forEach(this::injectInstance);

        return instance;
    }

    private boolean supportsDependencyBinding(Class<?> type) {
        return dependencyBindings.stream()
                .map(DependencyBinding::getDependencyType)
                .anyMatch(dependencyType -> dependencyType == type);
    }

    private void injectInstance(Field field) throws ManualInjectionException {
        Class<?> bindingType = dependencyBindings.stream()
                .filter(dependencyBinding -> dependencyBinding.getDependencyType() == field.getType())
                .findFirst()
                .map(DependencyBinding::getBindingType)
                .orElseThrow(() -> new ManualInjectionException(String.format(
                        "No dependency binding type found for field %s with type %s", field.getName(), field.getType().getName())));

        // Try creating an instance of the dependency.
        // It must have a default constructor; otherwise this step will fail.
        Object dependencyInstance;
        try {
            dependencyInstance = bindingType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            String message = String.format("Error creating instance of type %s for field %s", bindingType.toString(), field.toString());
            throw new ManualInjectionException(message);
        }

        // Make the annotated field accessible.
        boolean isAccessible = field.trySetAccessible();
        if (!isAccessible) {
            String message = String.format("Failed to make field %s accessible", field.getName());
            throw new ManualInjectionException(message);
        }

        // Inject the dependency into the class.
        try {
            field.set(instance, dependencyInstance);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to make field %s accessible", field.toString());
            throw new ManualInjectionException(message, e);
        }
    }

    private boolean canInject(Field field) {
        return field.getDeclaredAnnotation(Inject.class) != null || field.getDeclaredAnnotation(InjectManually.class) != null;
    }
}
