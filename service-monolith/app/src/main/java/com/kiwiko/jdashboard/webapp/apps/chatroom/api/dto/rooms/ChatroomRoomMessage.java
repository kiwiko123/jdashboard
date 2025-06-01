package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.rooms;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessage;

public class ChatroomRoomMessage {
    private ChatroomMessage chatroomMessage;
    private String senderDisplayName;

    public ChatroomMessage getChatroomMessage() {
        return chatroomMessage;
    }

    public void setChatroomMessage(ChatroomMessage chatroomMessage) {
        this.chatroomMessage = chatroomMessage;
    }

    public String getSenderDisplayName() {
        return senderDisplayName;
    }

    public void setSenderDisplayName(String senderDisplayName) {
        this.senderDisplayName = senderDisplayName;
    }
}
