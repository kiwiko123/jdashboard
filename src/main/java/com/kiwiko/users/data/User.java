package com.kiwiko.users.data;

import com.kiwiko.persistence.dataAccess.data.AuditableDataEntityDTO;

import javax.annotation.Nullable;
import java.util.Optional;

public class User extends AuditableDataEntityDTO {

    private String username;
    private @Nullable String emailAddress;
    private String encryptedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public void setEmailAddress(@Nullable String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
