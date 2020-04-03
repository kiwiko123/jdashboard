package com.kiwiko.mvc.security.authentication.api;

public interface PasswordService {

    String encryptPassword(String plaintext);

    boolean matches(String plainTextPassword, String encryptedPassword);
}
