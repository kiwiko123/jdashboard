package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters;

import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;

public class CreateTableRecordVersionOutput {
    private TableRecordVersion tableRecordVersion;

    public TableRecordVersion getTableRecordVersion() {
        return tableRecordVersion;
    }

    public void setTableRecordVersion(TableRecordVersion tableRecordVersion) {
        this.tableRecordVersion = tableRecordVersion;
    }
}
