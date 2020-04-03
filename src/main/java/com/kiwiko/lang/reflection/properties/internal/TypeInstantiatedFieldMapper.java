package com.kiwiko.lang.reflection.properties.internal;

import com.kiwiko.lang.reflection.properties.api.FieldMapper;

public class TypeInstantiatedFieldMapper<SourceType, TargetType> extends FieldMapper<SourceType, TargetType> {

    private Class<TargetType> targetType;

    public TypeInstantiatedFieldMapper<SourceType, TargetType> withTargetType(Class<TargetType> targetType) {
        this.targetType = targetType;
        return this;
    }

    @Override
    protected Class<TargetType> getTargetType() {
        return targetType;
    }
}
