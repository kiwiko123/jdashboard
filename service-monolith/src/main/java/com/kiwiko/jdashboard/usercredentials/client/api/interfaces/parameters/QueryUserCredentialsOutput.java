package com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.usercredentials.client.api.dto.UserCredential;

import java.util.Set;

public class QueryUserCredentialsOutput {
    private Set<UserCredential> userCredentials;

    public Set<UserCredential> getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(Set<UserCredential> userCredentials) {
        this.userCredentials = userCredentials;
    }
}
