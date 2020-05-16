package com.kiwiko.library.lang.reflection.properties.api;

/**
 * A class that uses reflection to copy fields from type A to type B, and type B to type A.
 *
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class BidirectionalFieldMapper<SourceType, TargetType>
        extends FieldMapper<SourceType, TargetType>
        implements BidirectionalPropertyMapper<SourceType, TargetType> {

    protected abstract Class<SourceType> getSourceType();

    @Override
    public void copyToSource(TargetType source, SourceType destination) {
        copyFieldsToObject(source, destination);
    }

    @Override
    public SourceType toSourceType(TargetType source) {
        SourceType result = createDefaultInstance(getSourceType());
        copyToSource(source, result);
        return result;
    }
}
