package com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces;

public interface DataEntityMapper<Entity, Dto> {

    void copyToEntity(Dto dto, Entity entity);
    Entity toEntity(Dto dto);

    void copyToDto(Entity entity, Dto dto);
    Dto toDto(Entity entity);
}
