package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters;

import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;

public class CreateTableRecordVersionInput {
    private final TableRecordVersion tableRecordVersion;

    public CreateTableRecordVersionInput(TableRecordVersion tableRecordVersion) {
        this.tableRecordVersion = tableRecordVersion;
    }

    public TableRecordVersion getTableRecordVersion() {
        return tableRecordVersion;
    }
}
