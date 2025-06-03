package com.kiwiko.jdashboard.users.service.logic.encryption;

public interface UserCredentialEncryptor {

    String encrypt(String plaintext);

    boolean matches(String plainText, String encrypted);
}
