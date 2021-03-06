package com.kiwiko.library.lang.reflection.properties.api;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.lang.reflection.properties.api.errors.PropertyMappingException;

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

    protected PartialPropertyMapper() {
        // For versatility (e.g., ease-of-use outside of a web application),
        // helpers are directly instantiated rather than using conventional dependency injection.
        reflectionHelper = new ReflectionHelper();
    }

    /**
     * Return the target/destination type as a {@link Class}.
     * This is needed to instantiate a new, default instance of its type.
     *
     * @return the target type as a class
     */
    protected abstract Class<TargetType> getTargetType();

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
        TargetType result = reflectionHelper.createDefaultInstance(getTargetType());
        copyToTarget(source, result);
        return result;
    }
}
