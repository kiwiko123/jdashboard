package com.kiwiko.persistence.dataAccess.api;

import com.kiwiko.persistence.identification.Identifiable;

import java.time.Instant;

public interface AuditableEntity extends Identifiable<Long> {

    void setId(Long id);

    Instant getCreatedDate();
    void setCreatedDate(Instant createdDate);

    Instant getLastUpdatedDate();
    void setLastUpdatedDate(Instant lastUpdatedDate);
}
