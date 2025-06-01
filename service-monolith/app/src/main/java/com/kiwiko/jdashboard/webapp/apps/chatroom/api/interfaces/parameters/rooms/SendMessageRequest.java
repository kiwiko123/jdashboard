package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

public class SendMessageRequest {
    private Long chatroomMessageRoomId;
    private String message;
    private Long senderUserId;

    public Long getChatroomMessageRoomId() {
        return chatroomMessageRoomId;
    }

    public void setChatroomMessageRoomId(Long chatroomMessageRoomId) {
        this.chatroomMessageRoomId = chatroomMessageRoomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }
}
