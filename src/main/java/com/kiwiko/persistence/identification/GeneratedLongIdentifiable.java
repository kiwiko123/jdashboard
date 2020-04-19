package com.kiwiko.persistence.identification;

import java.util.HashMap;
import java.util.Map;

public abstract class GeneratedLongIdentifiable extends TypeIdentifiable<Long> {

    private static Map<String, Long> ids = new HashMap<>();

    private Long id;

    public GeneratedLongIdentifiable() {
        String key = getClass().getName();
        long id = ids.getOrDefault(key, 1l);
        ids.put(key, id + 1);
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
