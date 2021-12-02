package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;

import java.util.Set;

public class ChatroomInboxItem {

    private ChatroomMessageRoom room;
    private Set<String> usernames;

    public ChatroomMessageRoom getRoom() {
        return room;
    }

    public void setRoom(ChatroomMessageRoom room) {
        this.room = room;
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Set<String> usernames) {
        this.usernames = usernames;
    }
}
