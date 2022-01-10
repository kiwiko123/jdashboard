package com.kiwiko.library.persistence.dataAccess.api;

import com.kiwiko.library.persistence.identification.Identifiable;

import java.time.Instant;

@Deprecated
public interface AuditableEntity extends Identifiable<Long> {

    void setId(Long id);

    Instant getCreatedDate();
    void setCreatedDate(Instant createdDate);

    Instant getLastUpdatedDate();
    void setLastUpdatedDate(Instant lastUpdatedDate);

    boolean getIsRemoved();
    void setIsRemoved(boolean isRemoved);
}
