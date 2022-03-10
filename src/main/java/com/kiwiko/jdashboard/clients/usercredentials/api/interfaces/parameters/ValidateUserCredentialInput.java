package com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters;

public class ValidateUserCredentialInput {
    private Long userCredentialId;
    private String plaintextCredentialValue;
    private String encryptionStrategy;

    public Long getUserCredentialId() {
        return userCredentialId;
    }

    public void setUserCredentialId(Long userCredentialId) {
        this.userCredentialId = userCredentialId;
    }

    public String getPlaintextCredentialValue() {
        return plaintextCredentialValue;
    }

    public void setPlaintextCredentialValue(String plaintextCredentialValue) {
        this.plaintextCredentialValue = plaintextCredentialValue;
    }

    public String getEncryptionStrategy() {
        return encryptionStrategy;
    }

    public void setEncryptionStrategy(String encryptionStrategy) {
        this.encryptionStrategy = encryptionStrategy;
    }
}
