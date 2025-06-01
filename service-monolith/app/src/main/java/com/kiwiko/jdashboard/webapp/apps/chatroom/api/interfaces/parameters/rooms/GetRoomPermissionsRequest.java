package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

public class GetRoomPermissionsRequest {
    private Long chatroomMessageRoomId;
    private Long userId;

    public Long getChatroomMessageRoomId() {
        return chatroomMessageRoomId;
    }

    public void setChatroomMessageRoomId(Long chatroomMessageRoomId) {
        this.chatroomMessageRoomId = chatroomMessageRoomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
