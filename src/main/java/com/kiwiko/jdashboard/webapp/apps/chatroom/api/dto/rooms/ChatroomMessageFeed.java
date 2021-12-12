package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms;

import java.util.List;

public class ChatroomMessageFeed {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
