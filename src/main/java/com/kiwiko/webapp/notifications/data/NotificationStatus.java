package com.kiwiko.webapp.notifications.data;

import com.kiwiko.library.lang.enums.EnumHelper;
import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Optional;

public enum NotificationStatus implements Identifiable<String> {
    SENT("sent"),
    RECEIVED("received"),
    REMOVED("removed");

    private final String id;

    NotificationStatus(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return id;
    }

    public static Optional<NotificationStatus> getById(String id) {
        return EnumHelper.getById(values(), id);
    }
}
