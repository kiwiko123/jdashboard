package com.kiwiko.jdashboard.library.lang.reflection.properties.api;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;

/**
 * A class that uses reflection to copy fields from type A to type B, and type B to type A.
 *
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class BidirectionalFieldMapper<SourceType, TargetType>
        extends FieldMapper<SourceType, TargetType>
        implements BidirectionalPropertyMapper<SourceType, TargetType> {

    private final ReflectionHelper reflectionHelper;
    private final Class<SourceType> sourceType;

    protected BidirectionalFieldMapper() {
        super();
        reflectionHelper = new ReflectionHelper();
        sourceType = getSourceType();
    }

    protected Class<SourceType> getSourceType() {
        return reflectionHelper.getGenericClassType(getClass(), 0);
    }

    @Override
    public void copyToSource(TargetType source, SourceType destination) {
        copyTo(source, destination);
    }

    @Override
    public SourceType toSourceType(TargetType source) {
        SourceType result = reflectionHelper.createDefaultInstance(sourceType);
        copyToSource(source, result);
        return result;
    }
}
