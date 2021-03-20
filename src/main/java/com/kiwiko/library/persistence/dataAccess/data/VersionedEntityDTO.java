package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.persistence.dataAccess.api.versions.Version;

import java.util.LinkedList;
import java.util.List;

public abstract class VersionedEntityDTO extends AuditableDataEntityDTO {

    private LinkedList<Version> versions;

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = new LinkedList<>(versions);
    }
}
