package com.kiwiko.library.persistence.data.properties.api.interfaces;

import com.kiwiko.library.lang.reflection.properties.api.BidirectionalPropertyMapper;

public interface DataEntityMapper<Entity, Dto> extends BidirectionalPropertyMapper<Entity, Dto> {

    void copyToEntity(Dto dto, Entity entity);
    Entity toEntity(Dto dto);

    void copyToDto(Entity entity, Dto dto);
    Dto toDto(Entity entity);

    @Override
    default void copyToSource(Dto source, Entity destination) {
        copyToEntity(source, destination);
    }

    @Override
    default Entity toSourceType(Dto source) {
        return toEntity(source);
    }

    @Override
    default void copyToTarget(Entity source, Dto destination) {
        copyToDto(source, destination);
    }

    @Override
    default Dto toTargetType(Entity source) {
        return toDto(source);
    }
}
