package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;

import java.util.Set;

public class GetLastUpdatedOutput {
    private Set<TableRecordVersion> lastUpdatedVersions;

    public Set<TableRecordVersion> getLastUpdatedVersions() {
        return lastUpdatedVersions;
    }

    public void setLastUpdatedVersions(Set<TableRecordVersion> lastUpdatedVersions) {
        this.lastUpdatedVersions = lastUpdatedVersions;
    }
}
