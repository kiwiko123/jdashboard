package com.kiwiko.persistence.dataAccess.data;

import com.kiwiko.persistence.dataAccess.api.AuditableEntity;

import java.time.Instant;
import java.util.Objects;

public abstract class AuditableDataEntityDTO implements AuditableEntity {

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
        return false;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !AuditableDataEntityDTO.class.isAssignableFrom(other.getClass())) {
            return false;
        }

        AuditableDataEntityDTO otherDTO = (AuditableDataEntityDTO) other;
        return Objects.equals(getId(), otherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}