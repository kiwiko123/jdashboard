package com.kiwiko.jdashboard.framework.dataaccess.mapper;

public abstract class AbstractDataEntityMapper<Entity, Dto>
        implements DataEntityMapper<Entity, Dto> {
    @Override
    public void copyToEntity(Dto dto, Entity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void copyToDto(Entity entity, Dto dto) {
        throw new UnsupportedOperationException();
    }
}
