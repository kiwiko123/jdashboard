package com.kiwiko.webapp.messages.data;

import com.kiwiko.library.lang.enums.EnumHelper;
import com.kiwiko.library.persistence.identification.Identifiable;

public enum MessageStatus implements Identifiable<String> {
    SENDING("sending"),
    SENT("sent"),
    DELIVERED("delivered"),
    FAILURE("failure");

    private final String id;

    MessageStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public static MessageStatus getById(String id) {
        return EnumHelper.getById(values(), id).orElse(null);
    }
}
