package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;

import java.util.List;

public class GetTableRecordVersionOutput {
    private List<TableRecordVersion> tableRecordVersions;

    public List<TableRecordVersion> getTableRecordVersions() {
        return tableRecordVersions;
    }

    public void setTableRecordVersions(List<TableRecordVersion> tableRecordVersions) {
        this.tableRecordVersions = tableRecordVersions;
    }
}
