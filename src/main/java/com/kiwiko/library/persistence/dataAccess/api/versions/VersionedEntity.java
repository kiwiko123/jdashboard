package com.kiwiko.library.persistence.dataAccess.api.versions;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;

import javax.persistence.Column;

public abstract class VersionedEntity extends DataEntity {

    private boolean isRemoved;
    private String versions;

    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean removed) {
        isRemoved = removed;
    }

    @Column(name = "versions")
    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
