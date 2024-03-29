package com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.SoftDeletableDataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;

public class ChatroomMessage extends SoftDeletableDataEntityDTO {

    private Long senderUserId;
    private Long chatroomMessageRoomId;
    private @Nullable String message;
    private @Nullable String messageStatus;
    private @Nullable Instant sentDate;

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getChatroomMessageRoomId() {
        return chatroomMessageRoomId;
    }

    public void setChatroomMessageRoomId(Long chatroomMessageRoomId) {
        this.chatroomMessageRoomId = chatroomMessageRoomId;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(@Nullable String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Nullable
    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(@Nullable Instant sentDate) {
        this.sentDate = sentDate;
    }
}
