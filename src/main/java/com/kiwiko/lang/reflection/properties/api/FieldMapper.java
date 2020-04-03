package com.kiwiko.lang.reflection.properties.api;

import com.kiwiko.lang.reflection.ReflectionHelper;
import com.kiwiko.lang.types.TypesHelper;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.impl.ConsoleLogService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

/**
 * A class that uses reflection to copy values from one object to another.
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class FieldMapper<SourceType, TargetType> implements PropertyMapper<SourceType, TargetType> {

    private final ReflectionHelper reflectionHelper;
    private final TypesHelper typesHelper;
    private final LogService logService;

    public FieldMapper() {
        reflectionHelper = new ReflectionHelper();
        typesHelper = new TypesHelper();
        logService = new ConsoleLogService();
    }

    protected abstract Class<TargetType> getTargetType();

    /**
     * {@inheritDoc}
     */
    @Override
    public void toTarget(SourceType source, TargetType target) {
        reflectionHelper.getFields(source.getClass()).stream()
                .filter(field -> canMap(field, target.getClass()))
                .forEach(field -> copyField(source, field, target));
    }

    @Override
    public TargetType toTargetType(SourceType source) {
        Constructor<TargetType> constructor;

        try {
            constructor = getTargetTypeConstructor();
        } catch (NoSuchMethodException e) {
            logService.error(String.format("No declared constructor found for target type %s", getTargetType().getName()), e);
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

    protected Constructor<TargetType> getTargetTypeConstructor() throws NoSuchMethodException {
        return getTargetType().getDeclaredConstructor();
    }

    /**
     * Given a declared field and a target class, determine if the target class has a field of the same name and type.
     *
     * @param sourceField the source class' declared field.
     * @param targetType the target class.
     * @return true if the target class has a declared field of the same name and type, or false if not.
     */
    private boolean canMap(Field sourceField, Class<?> targetType) {
        return reflectionHelper.findField(targetType, sourceField.getName())
                .map(Field::getType)
                .map(fieldType -> canMapFieldType(fieldType, sourceField.getType()))
                .orElse(false);
    }

    private boolean canMapFieldType(Class<?> fieldType, Class<?> sourceFieldType) {
        if (fieldType == sourceFieldType) {
            return true;
        }
        return Stream.of(fieldType, sourceFieldType)
                .map(type -> type.isPrimitive() ? typesHelper.getCorrespondingType(type) : type)
                .distinct()
                .count() == 1l;
    }

    private void copyField(SourceType source, Field field, TargetType target) {
        Field targetField = reflectionHelper.findField(target.getClass(), field.getName())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such field \"%s\"", field.getName())));

        // Make the fields accessible.
        field.trySetAccessible();
        targetField.trySetAccessible();

        // Copy the value from the source field to the target field.
        try {
            targetField.set(target, field.get(source));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
