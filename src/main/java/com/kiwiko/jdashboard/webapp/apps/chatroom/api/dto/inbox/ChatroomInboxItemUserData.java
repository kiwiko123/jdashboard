package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.inbox;

import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;

public class ChatroomInboxItemUserData {
    public static ChatroomInboxItemUserData fromUser(User user) {
        ChatroomInboxItemUserData data = new ChatroomInboxItemUserData();
        data.setUserId(user.getId());
        data.setUsername(user.getUsername());
        return data;
    }

    private Long userId;
    private String username;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
