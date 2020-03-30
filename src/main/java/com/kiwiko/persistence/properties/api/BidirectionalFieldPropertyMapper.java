package com.kiwiko.persistence.properties.api;

import com.kiwiko.persistence.properties.internal.TypeInstantiatedFieldPropertyMapper;

public abstract class BidirectionalFieldPropertyMapper<SourceType, TargetType> implements BidirectionalPropertyMapper<SourceType, TargetType> {

    private final PropertyMapper<SourceType, TargetType> sourceToTargetPropertyMapper;
    private final PropertyMapper<TargetType, SourceType> targetToSourcePropertyMapper;

    public BidirectionalFieldPropertyMapper() {
        sourceToTargetPropertyMapper = new TypeInstantiatedFieldPropertyMapper<>(getTargetType());
        targetToSourcePropertyMapper = new TypeInstantiatedFieldPropertyMapper<>(getSourceType());
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
