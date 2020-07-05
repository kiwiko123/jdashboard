package com.kiwiko.library.lang.reflection.properties.api;

import com.kiwiko.library.lang.reflection.ReflectionHelper;

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

    protected BidirectionalFieldMapper() {
        super();
        reflectionHelper = new ReflectionHelper();
    }

    protected abstract Class<SourceType> getSourceType();

    @Override
    public void copyToSource(TargetType source, SourceType destination) {
        copyTo(source, destination);
    }

    @Override
    public SourceType toSourceType(TargetType source) {
        SourceType result = reflectionHelper.createDefaultInstance(getSourceType());
        copyToSource(source, result);
        return result;
    }
}
