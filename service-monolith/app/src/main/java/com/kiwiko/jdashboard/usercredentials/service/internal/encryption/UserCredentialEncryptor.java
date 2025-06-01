package com.kiwiko.jdashboard.usercredentials.service.internal.encryption;

public interface UserCredentialEncryptor {

    String encryptPassword(String plaintext);

    boolean matches(String plainTextPassword, String encryptedPassword);
}
