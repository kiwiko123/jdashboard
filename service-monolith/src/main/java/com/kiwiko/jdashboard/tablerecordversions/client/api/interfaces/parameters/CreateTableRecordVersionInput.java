package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;

public class CreateTableRecordVersionInput {
    private final TableRecordVersion tableRecordVersion;

    public CreateTableRecordVersionInput(TableRecordVersion tableRecordVersion) {
        this.tableRecordVersion = tableRecordVersion;
    }

    public TableRecordVersion getTableRecordVersion() {
        return tableRecordVersion;
    }
}
