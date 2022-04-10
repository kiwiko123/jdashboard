package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters;

import java.util.Set;

public class GetLastUpdatedInput {
    private Set<VersionRecord> versionRecords;

    public Set<VersionRecord> getVersionRecords() {
        return versionRecords;
    }

    public void setVersionRecords(Set<VersionRecord> versionRecords) {
        this.versionRecords = versionRecords;
    }
}
