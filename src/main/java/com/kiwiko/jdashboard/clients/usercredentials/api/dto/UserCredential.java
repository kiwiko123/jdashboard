package com.kiwiko.jdashboard.clients.usercredentials.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;

import java.time.Instant;

public class UserCredential extends SoftDeletableDataEntityDTO {

    private Long userId;
    private String credentialType;
    private String credentialValue;
    private Instant createdDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialValue() {
        return credentialValue;
    }

    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
