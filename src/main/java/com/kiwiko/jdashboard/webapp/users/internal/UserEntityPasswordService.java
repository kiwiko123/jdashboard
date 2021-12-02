package com.kiwiko.jdashboard.webapp.users.internal;

import com.kiwiko.jdashboard.webapp.mvc.security.authentication.api.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEntityPasswordService implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public UserEntityPasswordService() {
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
