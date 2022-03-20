package com.kiwiko.jdashboard.services.featureflags.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feature_flags")
@CaptureDataChanges
public class FeatureFlagEntity implements DataEntity, SoftDeletable {

    private Long id;
    private String name;
    private String status;
    private @Nullable String value;
    private String userScope;
    private @Nullable Long userId;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_flag_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "flag_value")
    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    @Column(name = "user_scope", nullable = false)
    public String getUserScope() {
        return userScope;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope;
    }

    @Column(name = "user_id")
    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Override
    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
