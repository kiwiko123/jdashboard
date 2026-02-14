package com.kiwiko.jdashboard.users.service.logic.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class BCryptUserCredentialEncryptor implements UserCredentialEncryptor {

    private final PasswordEncoder passwordEncoder;

    public BCryptUserCredentialEncryptor() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String plaintext) {
        return passwordEncoder.encode(plaintext);
    }

    @Override
    public boolean matches(String plainText, String encrypted) {
        return passwordEncoder.matches(plainText, encrypted);
    }
}
