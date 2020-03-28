package com.kiwiko.mvc.lifecycle.startup.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AnnotationProcessor<A extends Annotation> extends ClassProcessor {

    protected abstract Class<A> getAnnotationType();

    protected void processAnnotatedClass(Class<?> clazz) { }

    protected void processAnnotatedMethod(Method method) { }

    protected void processAnnotatedField(Field field) { }

    // TODO support constructors and other ElementTypes
    @Override
    protected void processClass(Class<?> clazz) {
        if (!canProcessElement(ElementType.TYPE)) {
            return;
        }
        processAnnotatedClass(clazz);
    }

    @Override
    protected void processMethods(Collection<Method> methods) {
        if (!canProcessElement(ElementType.METHOD)) {
            return;
        }
        methods.stream()
                .filter(this::canProcessAccessibleObject)
                .forEach(this::processAnnotatedMethod);
    }

    @Override
    protected void processFields(Collection<Field> fields) {
        if (!canProcessElement(ElementType.FIELD)) {
            return;
        }
        fields.stream()
                .filter(this::canProcessAccessibleObject)
                .forEach(this::processAnnotatedField);
    }

    private boolean canProcessElement(ElementType elementType) {
        Target[] targets = getAnnotationType().getDeclaredAnnotationsByType(Target.class);
        Set<ElementType> supportedElementTypes = Arrays.stream(targets)
                .map(target -> target.value())
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());

        return supportedElementTypes.contains(elementType);
    }

    private boolean canProcessAccessibleObject(AccessibleObject accessibleObject) {
        return accessibleObject.getDeclaredAnnotation(getAnnotationType()) != null;
    }
}
