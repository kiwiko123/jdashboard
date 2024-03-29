package com.kiwiko.jdashboard.webapp.persistence.data.versions.api.interfaces.parameters;

public class GetTableRecordVersions {
    private final String tableName;
    private final Long recordId;

    public GetTableRecordVersions(String tableName, Long recordId) {
        this.tableName = tableName;
        this.recordId = recordId;
    }

    public String getTableName() {
        return tableName;
    }

    public Long getRecordId() {
        return recordId;
    }
}
