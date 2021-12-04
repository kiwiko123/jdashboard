package com.kiwiko.jdashboard.webapp.persistence.data.versions.internal;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.internal.data.TableRecordVersionEntity;

public class TableRecordVersionEntityMapper extends EntityMapper<TableRecordVersionEntity, TableRecordVersion> {

    @Override
    protected Class<TableRecordVersionEntity> getEntityType() {
        return TableRecordVersionEntity.class;
    }

    @Override
    protected Class<TableRecordVersion> getDTOType() {
        return TableRecordVersion.class;
    }
}
