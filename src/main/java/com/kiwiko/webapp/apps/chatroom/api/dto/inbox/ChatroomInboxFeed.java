package com.kiwiko.webapp.apps.chatroom.api.dto.inbox;

import java.util.List;

public class ChatroomInboxFeed {

    private List<ChatroomInboxItem> inboxItems;

    public List<ChatroomInboxItem> getInboxItems() {
        return inboxItems;
    }

    public void setInboxItems(List<ChatroomInboxItem> inboxItems) {
        this.inboxItems = inboxItems;
    }
}
