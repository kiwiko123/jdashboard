package com.kiwiko.persistence.identification;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class GeneratedLongIdentifiable extends TypeIdentifiable<Long> {

    private static Map<String, Long> ids = new HashMap<>();

    public GeneratedLongIdentifiable() {
        super();
    }

    @Override
    protected Optional<Long> calculateId() {
        String key = getClass().getName();
        long id = ids.getOrDefault(key, 1l);
        ids.put(key, id + 1);
        return Optional.of(id);
    }
}
