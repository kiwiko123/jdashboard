package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessage;

public class SendMessageResponse {
    private ChatroomMessage sentMessage;

    public ChatroomMessage getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(ChatroomMessage sentMessage) {
        this.sentMessage = sentMessage;
    }
}
