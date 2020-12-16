package com.kiwiko.webapp.push.data;

public enum PushServiceIdentifier {
    CHATROOM("chatroom");

    private final String id;

    PushServiceIdentifier(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
