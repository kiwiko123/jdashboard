package com.kiwiko.webapp.chatroom.internal.data;

public class ChatroomPushMessageData {

    private Long messageId;

    public Long getMessageId() {
        return messageId;
    }

    public ChatroomPushMessageData withMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }
}
