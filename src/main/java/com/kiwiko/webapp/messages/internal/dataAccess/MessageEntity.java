package com.kiwiko.webapp.messages.internal.dataAccess;

import com.kiwiko.library.persistence.dataAccess.api.versions.VersionedEntity;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.data.MessageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "messages")
public class MessageEntity extends VersionedEntity {

    private Long id;
    private String message;
    private MessageType messageType;
    private MessageStatus messageStatus;
    private Long senderUserId;
    private Long recipientUserId;
    private Instant sentDate;
    private boolean isRemoved;
    private String versions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "message", nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "message_type_id", nullable = false)
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status_id", nullable = false)
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Column(name = "sender_user_id", nullable = false)
    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    @Column(name = "recipient_user_id", nullable = false)
    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    @Column(name = "sent_date", nullable = false)
    public Instant getSentDate() {
        return sentDate;
    }

    public void setSentDate(Instant sentDate) {
        this.sentDate = sentDate;
    }

    @Column(name = "is_removed", nullable = false)
    @Override
    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean removed) {
        isRemoved = removed;
    }

    @Column(name = "versions")
    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
