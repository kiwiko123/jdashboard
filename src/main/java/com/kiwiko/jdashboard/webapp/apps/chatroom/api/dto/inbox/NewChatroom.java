package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;

import java.util.Set;

public class NewChatroom {
    private ChatroomMessageRoom chatroomMessageRoom;
    private Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers;

    public ChatroomMessageRoom getChatroomMessageRoom() {
        return chatroomMessageRoom;
    }

    public void setChatroomMessageRoom(ChatroomMessageRoom chatroomMessageRoom) {
        this.chatroomMessageRoom = chatroomMessageRoom;
    }

    public Set<ChatroomMessageRoomUser> getChatroomMessageRoomUsers() {
        return chatroomMessageRoomUsers;
    }

    public void setChatroomMessageRoomUsers(Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers) {
        this.chatroomMessageRoomUsers = chatroomMessageRoomUsers;
    }
}
