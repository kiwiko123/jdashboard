package com.kiwiko.persistence.identification;

import java.util.HashMap;
import java.util.Map;

public class GeneratedLongIdentifiable extends TypeIdentifiable<Long> {

    private static Map<String, Long> ids = new HashMap<>();

    public GeneratedLongIdentifiable() {
        String key = getClass().getName();
        long id = ids.getOrDefault(key, 1l);
        setId(id);
        ids.put(key, id + 1);
    }
}
