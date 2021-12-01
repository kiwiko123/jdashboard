package com.kiwiko.webapp.apps.chatroom.internal.data.entities;

import com.kiwiko.library.persistence.data.api.interfaces.SoftDeletableDataEntity;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.CaptureDataChanges;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "chatroom_message_rooms")
@CaptureDataChanges
public class ChatroomMessageEntity implements SoftDeletableDataEntity {

    private Long id;
    private Long senderUserId;
    private Long chatroomMessageRoomId;
    private @Nullable String messageStatus;
    private @Nullable Instant sentDate;
    private boolean isRemoved;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", nullable = false)
    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    @Column(name = "chatroom_message_room_id", nullable = false)
    public Long getChatroomMessageRoomId() {
        return chatroomMessageRoomId;
    }

    public void setChatroomMessageRoomId(Long chatroomMessageRoomId) {
        this.chatroomMessageRoomId = chatroomMessageRoomId;
    }

    @Nullable
    @Column(name = "message_status")
    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(@Nullable String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Nullable
    @Column(name = "sent_date")
    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(@Nullable Instant sentDate) {
        this.sentDate = sentDate;
    }

    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
