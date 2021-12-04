package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;

import java.time.Instant;
import java.util.Set;

public class ChatroomInboxItem {

    private ChatroomMessageRoom room;
    private Set<ChatroomInboxItemUserData> users;
    private Instant lastUpdatedDate;

    public ChatroomMessageRoom getRoom() {
        return room;
    }

    public void setRoom(ChatroomMessageRoom room) {
        this.room = room;
    }

    public Set<ChatroomInboxItemUserData> getUsers() {
        return users;
    }

    public void setUsers(Set<ChatroomInboxItemUserData> users) {
        this.users = users;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
