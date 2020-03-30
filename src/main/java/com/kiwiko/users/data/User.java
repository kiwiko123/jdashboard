package com.kiwiko.users.data;

import com.kiwiko.persistence.dataAccess.data.DataEntityDTO;

public class User extends DataEntityDTO {

    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
