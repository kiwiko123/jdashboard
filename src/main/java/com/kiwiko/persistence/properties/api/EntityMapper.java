package com.kiwiko.persistence.properties.api;

import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.lang.reflection.properties.api.errors.PropertyMappingException;

/**
 * Wrapper around {@link BidirectionalFieldMapper} that clarifies object mapping for JPA entities and corresponding Data Transfer Object classes.
 * 
 *
 * @param <Entity> the JPA entity class from which fields can be copied
 * @param <DTO> the DTO class to which fields can be copied
 */
public abstract class EntityMapper<Entity, DTO> extends BidirectionalFieldMapper<Entity, DTO> {

    protected abstract Class<Entity> getEntityType();
    protected abstract Class<DTO> getDTOType();

    public void copyToDTO(Entity entity, DTO dto) {
        super.copyToTarget(entity, dto);
    }

    public DTO toDTO(Entity entity) {
        DTO dto = createDefaultInstance(getDTOType());
        copyToDTO(entity, dto);
        return dto;
    }

    public void copyToEntity(DTO dto, Entity entity) {
        super.copyToSource(dto, entity);
    }

    public Entity toEntity(DTO dto) {
        Entity entity = createDefaultInstance(getEntityType());
        copyToEntity(dto, entity);
        return entity;
    }
    
    @Override
    protected final Class<Entity> getSourceType() {
        return getEntityType();
    }

    @Override
    protected final Class<DTO> getTargetType() {
        return getDTOType();
    }

    /**
     * @deprecated prefer {@link #copyToEntity(Object, Object)}
     * @see #copyToEntity(Object, Object) 
     */
    @Deprecated
    @Override
    public final void copyToSource(DTO source, Entity destination) {
        copyToEntity(source, destination);
    }

    /**
     * @deprecated prefer {@link #toEntity(Object)}
     * @see #toEntity(Object) 
     */
    @Deprecated
    @Override
    public final Entity toSourceType(DTO source) {
        return toEntity(source);
    }

    /**
     * @deprecated prefer {@link #copyToDTO(Object, Object)}
     * @see #toDTO(Object) 
     */
    @Deprecated
    @Override
    public final void copyToTarget(Entity source, DTO destination) throws PropertyMappingException {
        copyToDTO(source, destination);
    }

    /**
     * @deprecated prefer {@link #toDTO(Object)}
     * @see #toDTO(Object) 
     */
    @Deprecated
    @Override
    public final DTO toTargetType(Entity source) throws PropertyMappingException {
        return toDTO(source);
    }
}
