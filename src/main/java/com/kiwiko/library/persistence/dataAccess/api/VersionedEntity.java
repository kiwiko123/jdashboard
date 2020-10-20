package com.kiwiko.library.persistence.dataAccess.api;

import javax.persistence.Column;

public abstract class VersionedEntity extends DataEntity {

    private String versions;

    @Column(name = "versions")
    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
