package com.kiwiko.webapp.messages.data;

import com.kiwiko.library.lang.enums.EnumHelper;
import com.kiwiko.library.persistence.identification.Identifiable;

public enum MessageType implements Identifiable<Integer> {
    CHATROOM(1, "Chatroom");

    private final Integer id;
    private final String name;

    MessageType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MessageType getById(int id) {
        return EnumHelper.getById(values(), id).orElse(null);
    }
}
