package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters;

import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;

import java.time.Instant;
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
