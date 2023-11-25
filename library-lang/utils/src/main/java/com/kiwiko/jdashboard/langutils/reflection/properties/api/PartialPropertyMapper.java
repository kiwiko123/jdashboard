package com.kiwiko.jdashboard.langutils.reflection.properties.api;

import com.kiwiko.jdashboard.langutils.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.langutils.reflection.properties.api.errors.PropertyMappingException;

/**
 * A base class that provides a partial implementation of a property mapper.
 * The "copy to" and "create new instance" operations are already provided;
 * it only requires an implementation for actually copying properties from one type to the other.
 *
 * @param <SourceType> the source type from which properties can be copied
 * @param <TargetType> the target type to which properties can be copied
 */
public abstract class PartialPropertyMapper<SourceType, TargetType> implements PropertyMapper<SourceType, TargetType> {

    private final ReflectionHelper reflectionHelper;
    private final Class<TargetType> targetType;

    protected PartialPropertyMapper() {
        reflectionHelper = new ReflectionHelper();
        targetType = getTargetType();
    }

    /**
     * Return the target/destination type as a {@link Class}.
     * This is needed to instantiate a new, default instance of its type.
     * By default, this is automatically inferred through reflection.
     *
     * @return the target type as a class
     */
    protected Class<TargetType> getTargetType() {
        return reflectionHelper.getGenericClassType(getClass(), 1);
    }

    /**
     * Copy all relevant properties from the source object to the destination object.
     * The destination object must be modified.
     *
     * @param source the object from which fields will be copied
     * @param destination the object to which fields will be copied
     * @param <T> the source type
     * @param <V> the destination type
     */
    protected abstract <T, V> void copyTo(T source, V destination);

    @Override
    public void copyToTarget(SourceType source, TargetType destination) throws PropertyMappingException {
        copyTo(source, destination);
    }

    @Override
    public TargetType toTargetType(SourceType source) throws PropertyMappingException {
        TargetType result = reflectionHelper.createDefaultInstance(targetType);
        copyToTarget(source, result);
        return result;
    }
}
