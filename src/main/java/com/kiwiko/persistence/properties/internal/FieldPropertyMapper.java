package com.kiwiko.persistence.properties.internal;

import com.kiwiko.persistence.properties.api.PropertyMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * A class that uses reflection to copy values from one object to another.
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class FieldPropertyMapper<SourceType, TargetType> implements PropertyMapper<SourceType, TargetType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void toTarget(SourceType source, TargetType target) {
        Arrays.stream(source.getClass().getDeclaredFields())
                .filter(field -> canMap(field, target.getClass()))
                .forEach(field -> copyField(source, field, target));
    }

    @Override
    public TargetType toTargetType(SourceType source) {
        Constructor<TargetType> constructor;

        try {
            constructor = getTargetType().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        }

        constructor.trySetAccessible();
        TargetType result;

        try {
            result = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }

        toTarget(source, result);
        return result;
    }

    protected abstract Class<TargetType> getTargetType();

    /**
     * Given a declared field and a target class, determine if the target class has a field of the same name and type.
     *
     * @param sourceDeclaredField the source class' declared field.
     * @param targetType the target class.
     * @return true if the target class has a declared field of the same name and type, or false if not.
     */
    private boolean canMap(Field sourceDeclaredField, Class<?> targetType) {
        return getDeclaredField(targetType, sourceDeclaredField.getName())
                .map(Field::getType)
                .map(fieldType -> fieldType == sourceDeclaredField.getType())
                .orElse(false);
    }

    private void copyField(SourceType source, Field declaredField, TargetType target) {
        Field targetField = getDeclaredField(target.getClass(), declaredField.getName())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such field \"%s\"", declaredField.getName())));

        // Make the fields accessible.
        declaredField.trySetAccessible();
        targetField.trySetAccessible();

        // Copy the value from the source field to the target field.
        try {
            targetField.set(target, declaredField.get(source));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Like {@link Class#getDeclaredField(String)}, but the {@link NoSuchFieldException} is swallowed.
     *
     * @see Class#getDeclaredField(String).
     */
    private Optional<Field> getDeclaredField(Class<?> type, String fieldName) {
        try {
            return Optional.of(type.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }
}
