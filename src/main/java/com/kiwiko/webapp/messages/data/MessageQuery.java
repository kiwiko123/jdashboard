package com.kiwiko.webapp.messages.data;

public class MessageQuery {

    private Long id;
    private MessageType messageType;
    private Long senderUserId;
    private Long recipientUserId;

    public Long getId() {
        return id;
    }

    public MessageQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public MessageQuery setMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public MessageQuery setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
        return this;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public MessageQuery setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
        return this;
    }
}
