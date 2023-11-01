package com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;

import java.time.Instant;

public class UniversalUniqueIdentifier extends DataEntityDTO {

    private String uuid;
    private String referenceKey;
    private Instant createdDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
