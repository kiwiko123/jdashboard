package com.kiwiko.jdashboard.services.tablerecordversions.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;

public class TableRecordVersion extends DataEntityDTO {
    private String tableName;
    private Long recordId;
    private @Nullable String changes;
    private Instant createdDate;
    private @Nullable Long createdByUserId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Nullable
    public String getChanges() {
        return changes;
    }

    public void setChanges(@Nullable String changes) {
        this.changes = changes;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Nullable
    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(@Nullable Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
