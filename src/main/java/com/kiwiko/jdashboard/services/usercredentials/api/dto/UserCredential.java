package com.kiwiko.jdashboard.services.usercredentials.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;

import java.time.Instant;

public class UserCredential extends SoftDeletableDataEntityDTO {

    private Long userId;
    private String credentialName;
    private String credentialType;
    private Instant createdDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
