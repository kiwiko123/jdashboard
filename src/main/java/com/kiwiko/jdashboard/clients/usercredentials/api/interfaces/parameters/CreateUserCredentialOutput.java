package com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters;

import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;

public class CreateUserCredentialOutput {
    private UserCredential userCredential;

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }
}
