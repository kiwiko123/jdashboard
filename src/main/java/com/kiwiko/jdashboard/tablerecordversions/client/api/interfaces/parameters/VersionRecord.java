package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters;

import java.util.Objects;

public class VersionRecord {
    private String tableName;
    private Long id;

    public VersionRecord(String tableName, Long id) {
        this.tableName = tableName;
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionRecord that = (VersionRecord) o;
        return Objects.equals(tableName, that.tableName) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, id);
    }
}
