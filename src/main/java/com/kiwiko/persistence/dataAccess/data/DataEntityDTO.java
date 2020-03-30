package com.kiwiko.persistence.dataAccess.data;

import com.kiwiko.persistence.identification.Identifiable;

import java.time.Instant;
import java.util.Objects;

public abstract class DataEntityDTO implements Identifiable<Long> {

    private Long id;
    private Instant createdDate;
    private Instant lastUpdatedDate;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !DataEntityDTO.class.isAssignableFrom(other.getClass())) {
            return false;
        }

        DataEntityDTO otherDTO = (DataEntityDTO) other;
        return Objects.equals(getId(), otherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}