package com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters;

public class CreateUserCredentialInput {
    private final long userId;
    private final String credentialType;
    private final String credentialValue;
    private String encryptionStrategy;

    public CreateUserCredentialInput(long userId, String credentialType, String credentialValue) {
        this.userId = userId;
        this.credentialType = credentialType;
        this.credentialValue = credentialValue;
    }

    public long getUserId() {
        return userId;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public String getCredentialValue() {
        return credentialValue;
    }

    public String getEncryptionStrategy() {
        return encryptionStrategy;
    }

    public void setEncryptionStrategy(String encryptionStrategy) {
        this.encryptionStrategy = encryptionStrategy;
    }
}
