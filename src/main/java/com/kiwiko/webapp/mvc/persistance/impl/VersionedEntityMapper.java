package com.kiwiko.webapp.mvc.persistance.impl;

import com.kiwiko.library.persistence.dataAccess.api.Version;
import com.kiwiko.library.persistence.dataAccess.api.VersionedEntity;
import com.kiwiko.library.persistence.dataAccess.data.VersionedEntityDTO;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;

import javax.inject.Inject;
import java.util.List;

public abstract class VersionedEntityMapper<Entity extends VersionedEntity, DTO extends VersionedEntityDTO>
        extends EntityMapper<Entity, DTO> {

    @Inject private JsonMapper jsonMapper;

    @Override
    public void copyToDTO(Entity entity, DTO dto) {
        super.copyToDTO(entity, dto);
        List<Version> versions = jsonMapper.convertCollectionValue(entity.getVersions(), List.class, Version.class);
        dto.setVersions(versions);
    }

    @Override
    public void copyToEntity(DTO dto, Entity entity) {
        super.copyToEntity(dto, entity);
        String dumpedVersions = jsonMapper.writeValueAsString(dto.getVersions());
        entity.setVersions(dumpedVersions);
    }
}
