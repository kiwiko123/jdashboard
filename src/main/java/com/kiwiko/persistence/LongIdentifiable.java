package com.kiwiko.persistence;

public abstract class LongIdentifiable extends Identifiable<Long> {

    private static long count = 1;

    public LongIdentifiable() {
        super(count++);
    }

    public LongIdentifiable(long id) {
        super(id);
    }
}
