package com.kiwiko.scrabble.data;

import java.util.concurrent.atomic.AtomicLong;

public class LongIncrementor {

    private static final AtomicLong count = new AtomicLong();

    public long increment() {
        return count.getAndIncrement();
    }
}
