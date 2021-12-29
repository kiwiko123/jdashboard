package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters;

import java.util.Set;

public class CreateChatroomFormInput {

    private Long userId;
    private Set<String> recipientUsernames;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<String> getRecipientUsernames() {
        return recipientUsernames;
    }

    public void setRecipientUsernames(Set<String> recipientUsernames) {
        this.recipientUsernames = recipientUsernames;
    }
}
