package com.kiwiko.jdashboard.services.usercredentials.internal.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultUserCredentialEncryptor implements UserCredentialEncryptor {

    private final PasswordEncoder passwordEncoder;

    public DefaultUserCredentialEncryptor() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encryptPassword(String plaintext) {
        return passwordEncoder.encode(plaintext);
    }

    @Override
    public boolean matches(String plainTextPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainTextPassword, encryptedPassword);
    }
}
