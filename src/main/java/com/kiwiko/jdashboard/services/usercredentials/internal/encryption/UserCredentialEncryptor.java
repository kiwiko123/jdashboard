package com.kiwiko.jdashboard.services.usercredentials.internal.encryption;

public interface UserCredentialEncryptor {

    String encryptPassword(String plaintext);

    boolean matches(String plainTextPassword, String encryptedPassword);
}
