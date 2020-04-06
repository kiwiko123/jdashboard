package com.kiwiko.users.data;

import com.kiwiko.persistence.dataAccess.data.AuditableDataEntityDTO;

public class User extends AuditableDataEntityDTO {

    private String emailAddress;
    private String encryptedPassword;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
