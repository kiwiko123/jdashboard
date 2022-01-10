package com.kiwiko.library.persistence.dataAccess.api;

import java.time.Instant;

@Deprecated
public abstract class AuditableDataEntity extends DataEntity implements AuditableEntity {

    public AuditableDataEntity() {
        Instant now = Instant.now();
        setCreatedDate(now);
        setLastUpdatedDate(now);
        setIsRemoved(false);
    }
}
