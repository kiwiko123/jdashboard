package com.kiwiko.jdashboard.services.usercredentials.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletable;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "user_credentials")
@CaptureDataChanges
public class UserCredentialEntity implements DataEntity, SoftDeletable {
    private Long id;
    private Long userId;
    private String credentialType;
    private String credentialValue;
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

    @Column(name = "credential_type", nullable = false)
    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    @Column(name = "credential_value", nullable = false)
    public String getCredentialValue() {
        return credentialValue;
    }

    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
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

    @Override
    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
