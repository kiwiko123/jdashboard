package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.parameters;

public class GetMessagesForRoomParameters {

    private Long chatroomMessageRoomId;
    private Integer maxMessagesToFetch;
    private boolean includeRemovedMessages = false;

    public Long getChatroomMessageRoomId() {
        return chatroomMessageRoomId;
    }

    public void setChatroomMessageRoomId(Long chatroomMessageRoomId) {
        this.chatroomMessageRoomId = chatroomMessageRoomId;
    }

    public Integer getMaxMessagesToFetch() {
        return maxMessagesToFetch;
    }

    public void setMaxMessagesToFetch(Integer maxMessagesToFetch) {
        this.maxMessagesToFetch = maxMessagesToFetch;
    }

    public boolean getIncludeRemovedMessages() {
        return includeRemovedMessages;
    }

    public void setIncludeRemovedMessages(boolean includeRemovedMessages) {
        this.includeRemovedMessages = includeRemovedMessages;
    }
}
