package com.kiwiko.lang.reflection.properties.api;

import com.kiwiko.lang.reflection.ReflectionHelper;
import com.kiwiko.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.lang.reflection.properties.internal.FieldMapperHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A class that uses reflection to copy values from one object to another.
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class FieldMapper<SourceType, TargetType> implements PropertyMapper<SourceType, TargetType> {

    private final FieldMapperHelper fieldMapperHelper;
    private final ReflectionHelper reflectionHelper;

    public FieldMapper() {
        fieldMapperHelper = new FieldMapperHelper();
        reflectionHelper = new ReflectionHelper();
    }

    protected abstract Class<TargetType> getTargetType();

    /**
     * {@inheritDoc}
     */
    @Override
    public void toTarget(SourceType source, TargetType destination) throws PropertyMappingException {
        copyFieldsToObject(source, destination);
    }

    @Override
    public TargetType toTargetType(SourceType source) throws PropertyMappingException {
        TargetType result = createDefaultInstance(getTargetType());
        toTarget(source, result);
        return result;
    }

    protected <T, V> void copyFieldsToObject(T source, V target) throws PropertyMappingException {
        reflectionHelper.getFields(source.getClass()).stream()
                .filter(field -> fieldMapperHelper.canCopyField(field, target.getClass()))
                .forEach(field -> fieldMapperHelper.copyField(field, source, target));
    }

    protected <T> T createDefaultInstance(Class<T> type) throws PropertyMappingException {
        Constructor<T> constructor;

        try {
            constructor = type.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            String message = String.format("No declared constructor found for target type %s", getTargetType().getName());
            throw new PropertyMappingException(message, e);
        }

        constructor.trySetAccessible();
        T result;

        try {
            result = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String message = String.format("Failed to create a new instance for target type %s", getTargetType().getName());
            throw new PropertyMappingException(message, e);
        }

        return result;
    }
}
