package com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal;

import java.util.UUID;

public class UuidGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
