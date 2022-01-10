package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.persistence.dataAccess.api.AuditableEntity;
import com.kiwiko.library.persistence.identification.TypeIdentifiable;

import java.time.Instant;

@Deprecated
public abstract class AuditableDataEntityDTO extends TypeIdentifiable<Long> implements AuditableEntity {

    private Long id;
    private Instant createdDate;
    private Instant lastUpdatedDate;
    private boolean isRemoved;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @Override
    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}