package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

public class GetMessageFeedParameters {

    private Long roomId;
    private Long userId;
    private int maxMessagesToFetch = 100;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMaxMessagesToFetch() {
        return maxMessagesToFetch;
    }

    public void setMaxMessagesToFetch(Integer maxMessagesToFetch) {
        this.maxMessagesToFetch = maxMessagesToFetch;
    }
}
