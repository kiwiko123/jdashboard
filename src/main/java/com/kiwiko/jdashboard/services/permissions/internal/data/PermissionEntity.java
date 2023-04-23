package com.kiwiko.jdashboard.services.permissions.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "permissions")
@CaptureDataChanges
public class PermissionEntity implements LongDataEntity, SoftDeletable {
    private Long id;
    private Long userId;
    private String permissionName;
    private Instant createdDate;
    private boolean isRemoved;

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

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "permission_name", nullable = false)
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "is_removed", nullable = false)
    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
