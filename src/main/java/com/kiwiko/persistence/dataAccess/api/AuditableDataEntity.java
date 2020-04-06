package com.kiwiko.persistence.dataAccess.api;

import java.time.Instant;

public abstract class AuditableDataEntity extends DataEntity implements AuditableEntity {

    public AuditableDataEntity() {
        Instant now = Instant.now();
        setCreatedDate(now);
        setLastUpdatedDate(now);
        setIsRemoved(false);
    }
}
