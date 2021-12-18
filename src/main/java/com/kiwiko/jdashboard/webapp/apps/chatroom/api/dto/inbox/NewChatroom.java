package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;

import java.util.Set;

public class NewChatroom {
    private ChatroomMessageRoom chatroomMessageRoom;
    private String chatroomMessageRoomUuid;
    private Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers;

    public ChatroomMessageRoom getChatroomMessageRoom() {
        return chatroomMessageRoom;
    }

    public void setChatroomMessageRoom(ChatroomMessageRoom chatroomMessageRoom) {
        this.chatroomMessageRoom = chatroomMessageRoom;
    }

    public String getChatroomMessageRoomUuid() {
        return chatroomMessageRoomUuid;
    }

    public void setChatroomMessageRoomUuid(String chatroomMessageRoomUuid) {
        this.chatroomMessageRoomUuid = chatroomMessageRoomUuid;
    }

    public Set<ChatroomMessageRoomUser> getChatroomMessageRoomUsers() {
        return chatroomMessageRoomUsers;
    }

    public void setChatroomMessageRoomUsers(Set<ChatroomMessageRoomUser> chatroomMessageRoomUsers) {
        this.chatroomMessageRoomUsers = chatroomMessageRoomUsers;
    }
}
