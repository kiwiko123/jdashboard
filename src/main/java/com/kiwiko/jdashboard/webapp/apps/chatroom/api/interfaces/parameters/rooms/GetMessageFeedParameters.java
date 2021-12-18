package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

public class GetMessageFeedParameters {

    private String roomUuid;
    private Long userId;
    private int maxMessagesToFetch = 100;

    public String getRoomUuid() {
        return roomUuid;
    }

    public void setRoomUuid(String roomUuid) {
        this.roomUuid = roomUuid;
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
