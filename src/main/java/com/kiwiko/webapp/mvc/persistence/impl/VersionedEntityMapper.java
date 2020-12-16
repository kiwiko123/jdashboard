package com.kiwiko.webapp.mvc.persistence.impl;

import com.kiwiko.library.persistence.dataAccess.api.versions.Version;
import com.kiwiko.library.persistence.dataAccess.api.versions.VersionedEntity;
import com.kiwiko.library.persistence.dataAccess.data.VersionedEntityDTO;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;

import javax.inject.Inject;
import java.util.List;

public abstract class VersionedEntityMapper<Entity extends VersionedEntity, DTO extends VersionedEntityDTO>
        extends EntityMapper<Entity, DTO> {

    @Inject private JsonMapper jsonMapper;
    @Inject private VersionConverterHelper versionConverterHelper;

    @Override
    public void copyToDTO(Entity entity, DTO dto) {
        super.copyToDTO(entity, dto);

        String versionsJson = entity.getVersions();
        if (versionsJson != null) {
            List<Version> versions = versionConverterHelper.deserializeVersionsJson(versionsJson);
            dto.setVersions(versions);
        }
    }

    @Override
    public void copyToEntity(DTO dto, Entity entity) {
        super.copyToEntity(dto, entity);

        List<Version> versions = dto.getVersions();
        if (versions != null) {
            String dumpedVersions = jsonMapper.writeValueAsString(dto.getVersions());
            entity.setVersions(dumpedVersions);
        }
    }
}
