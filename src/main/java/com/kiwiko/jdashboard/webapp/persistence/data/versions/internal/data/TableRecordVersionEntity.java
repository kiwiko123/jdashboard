package com.kiwiko.jdashboard.webapp.persistence.data.versions.internal.data;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "table_record_versions")
public class TableRecordVersionEntity extends DataEntity {
    private Long id;
    private String tableName;
    private Long recordId;
    private @Nullable String changes;
    private Instant createdDate;
    private @Nullable Long createdByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "table_name", nullable = false)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Column(name = "record_id", nullable = false)
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Nullable
    @Column(name = "changes")
    public String getChanges() {
        return changes;
    }

    public void setChanges(@Nullable String changes) {
        this.changes = changes;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Nullable
    @Column(name = "created_by_user_id")
    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(@Nullable Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
