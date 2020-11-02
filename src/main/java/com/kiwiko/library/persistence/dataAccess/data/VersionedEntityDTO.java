package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.persistence.dataAccess.api.versions.Version;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class VersionedEntityDTO extends AuditableDataEntityDTO {

    private LinkedList<Version> versions;

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = new LinkedList<>(versions);
    }

    @Override
    public Instant getCreatedDate() {
        return Optional.ofNullable(versions.peekFirst())
                .map(Version::getCreatedDate)
                .orElseGet(Instant::now);
    }

    @Override
    public Instant getLastUpdatedDate() {
        return Optional.ofNullable(versions.peekLast())
                .map(Version::getCreatedDate)
                .orElse(null);
    }
}
