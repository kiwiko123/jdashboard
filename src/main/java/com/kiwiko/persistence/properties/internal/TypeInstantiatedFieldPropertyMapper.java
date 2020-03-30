package com.kiwiko.persistence.properties.internal;

import com.kiwiko.persistence.properties.api.FieldPropertyMapper;

public class TypeInstantiatedFieldPropertyMapper<SourceType, TargetType> extends FieldPropertyMapper<SourceType, TargetType> {

    private final Class<TargetType> targetType;

    public TypeInstantiatedFieldPropertyMapper(Class<TargetType> targetType) {
        this.targetType = targetType;
    }

    @Override
    protected Class<TargetType> getTargetType() {
        return targetType;
    }
}
