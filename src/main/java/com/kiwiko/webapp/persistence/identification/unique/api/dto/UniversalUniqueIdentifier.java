package com.kiwiko.webapp.persistence.identification.unique.api.dto;

import com.kiwiko.library.persistence.data.api.interfaces.DataEntityDTO;

import java.time.Instant;

public class UniversalUniqueIdentifier extends DataEntityDTO {

    private String uuid;
    private String referencedTableName;
    private Long referencedId;
    private Instant createdDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public Long getReferencedId() {
        return referencedId;
    }

    public void setReferencedId(Long referencedId) {
        this.referencedId = referencedId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
