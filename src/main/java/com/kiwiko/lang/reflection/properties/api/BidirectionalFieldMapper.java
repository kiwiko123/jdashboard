package com.kiwiko.lang.reflection.properties.api;

import com.kiwiko.lang.reflection.properties.internal.TypeInstantiatedFieldMapper;

public abstract class BidirectionalFieldMapper<SourceType, TargetType> implements BidirectionalPropertyMapper<SourceType, TargetType> {

    private final TypeInstantiatedFieldMapper<SourceType, TargetType> sourceToTargetPropertyMapper;
    private final TypeInstantiatedFieldMapper<TargetType, SourceType> targetToSourcePropertyMapper;

    public BidirectionalFieldMapper() {
        this.sourceToTargetPropertyMapper = new TypeInstantiatedFieldMapper<SourceType, TargetType>()
                .withTargetType(getTargetType());
        this.targetToSourcePropertyMapper = new TypeInstantiatedFieldMapper<TargetType, SourceType>()
                .withTargetType(getSourceType());
    }

    protected abstract Class<SourceType> getSourceType();
    protected abstract Class<TargetType> getTargetType();

    @Override
    public void toTarget(SourceType source, TargetType target) {
        sourceToTargetPropertyMapper.toTarget(source, target);
    }

    @Override
    public TargetType toTargetType(SourceType source) {
        return sourceToTargetPropertyMapper.toTargetType(source);
    }

    @Override
    public void toSource(TargetType target, SourceType source) {
        targetToSourcePropertyMapper.toTarget(target, source);
    }

    @Override
    public SourceType toSourceType(TargetType target) {
        return targetToSourcePropertyMapper.toTargetType(target);
    }
}
