package com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.library.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.jdashboard.library.lang.reflection.properties.api.errors.PropertyMappingException;

public abstract class DataEntityFieldMapper<Entity, Dto>
        extends BidirectionalFieldMapper<Entity, Dto>
        implements DataEntityMapper<Entity, Dto> {

    private final ReflectionHelper reflectionHelper;

    protected DataEntityFieldMapper() {
        super();
        reflectionHelper = new ReflectionHelper();
    }

    @Override
    public void copyToEntity(Dto dto, Entity entity) {
        copyToSource(dto, entity);
    }

    @Override
    public Entity toEntity(Dto dto) {
        Entity entity = reflectionHelper.createDefaultInstance(getSourceType());
        copyToEntity(dto, entity);
        return entity;
    }

    @Override
    public void copyToDto(Entity entity, Dto dto) {
        copyToTarget(entity, dto);
    }

    @Override
    public Dto toDto(Entity entity) {
        Dto dto = reflectionHelper.createDefaultInstance(getTargetType());
        copyToDto(entity, dto);
        return dto;
    }

    /**
     * @deprecated prefer {@link #copyToEntity(Object, Object)}
     */
    @Deprecated
    @Override
    public final void copyToSource(Dto source, Entity destination) {
        super.copyToSource(source, destination);
    }

    /**
     * @deprecated prefer {@link #toEntity(Object)}
     */
    @Deprecated
    @Override
    public final Entity toSourceType(Dto source) {
        return super.toSourceType(source);
    }

    /**
     * @deprecated prefer {@link #copyToDto(Object, Object)}
     */
    @Deprecated
    @Override
    public final void copyToTarget(Entity source, Dto destination) throws PropertyMappingException {
        super.copyToTarget(source, destination);
    }

    /**
     * @deprecated prefer {@link #toDto(Object)}
     */
    @Deprecated
    @Override
    public final Dto toTargetType(Entity source) throws PropertyMappingException {
        return super.toTargetType(source);
    }
}
