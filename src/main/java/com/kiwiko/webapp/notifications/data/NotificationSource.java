package com.kiwiko.webapp.notifications.data;

import com.kiwiko.library.lang.enums.EnumHelper;
import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Optional;

public enum NotificationSource implements Identifiable<String> {
    PUSH_SERVICE("pushService");

    private final String id;

    NotificationSource(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return id;
    }

    public static Optional<NotificationSource> getById(String id) {
        return EnumHelper.getById(values(), id);
    }
}
