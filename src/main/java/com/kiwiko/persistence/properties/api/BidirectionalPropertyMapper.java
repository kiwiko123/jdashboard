package com.kiwiko.persistence.properties.api;

public interface BidirectionalPropertyMapper<SourceType, TargetType> extends PropertyMapper<SourceType, TargetType> {

    void toSource(TargetType target, SourceType source);
    SourceType toSourceType(TargetType target);
}
