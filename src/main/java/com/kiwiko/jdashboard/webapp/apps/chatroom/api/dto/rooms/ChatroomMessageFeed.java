package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms;

import java.util.List;

public class ChatroomMessageFeed {
    private List<ChatroomRoomMessage> messages;

    public List<ChatroomRoomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatroomRoomMessage> messages) {
        this.messages = messages;
    }
}
