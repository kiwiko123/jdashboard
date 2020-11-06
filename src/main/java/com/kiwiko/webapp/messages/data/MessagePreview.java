package com.kiwiko.webapp.messages.data;

import java.util.List;

public class MessagePreview {

    private List<MessagePreviewUser> users;
    private Message message;

    public List<MessagePreviewUser> getUsers() {
        return users;
    }

    public void setUsers(List<MessagePreviewUser> users) {
        this.users = users;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
