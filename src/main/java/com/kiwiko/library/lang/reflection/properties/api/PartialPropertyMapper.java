package com.kiwiko.library.lang.reflection.properties.api;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.lang.reflection.properties.api.errors.PropertyMappingException;

public abstract class PartialPropertyMapper<SourceType, TargetType> implements PropertyMapper<SourceType, TargetType> {

    protected final ReflectionHelper reflectionHelper;

    public PartialPropertyMapper() {
        reflectionHelper = new ReflectionHelper();
    }

    
    protected abstract Class<TargetType> getTargetType();

    protected abstract <T, V> void copyTo(T source, V destination);

    @Override
    public void copyToTarget(SourceType source, TargetType destination) {
        copyTo(source, destination);
    }

    @Override
    public TargetType toTargetType(SourceType source) throws PropertyMappingException {
        TargetType result = reflectionHelper.createDefaultInstance(getTargetType());
        copyToTarget(source, result);
        return result;
    }
}
