package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;

public class CreateTableRecordVersionOutput {
    private TableRecordVersion tableRecordVersion;

    public TableRecordVersion getTableRecordVersion() {
        return tableRecordVersion;
    }

    public void setTableRecordVersion(TableRecordVersion tableRecordVersion) {
        this.tableRecordVersion = tableRecordVersion;
    }
}
