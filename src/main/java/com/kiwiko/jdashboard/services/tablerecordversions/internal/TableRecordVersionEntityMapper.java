package com.kiwiko.jdashboard.services.tablerecordversions.internal;

import com.kiwiko.jdashboard.library.persistence.properties.api.EntityMapper;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.data.TableRecordVersionEntity;

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
