package com.kiwiko.mvc.requests.data;

import com.kiwiko.persistence.LongIdentifiable;

import java.time.Instant;

public class RequestContext extends LongIdentifiable {

    private final int count;
    private final Instant instant;

    public RequestContext(int count, Instant instant) {
        super();
        this.count = count;
        this.instant = instant;
    }

    public int getCount() {
        return count;
    }

    public Instant getInstant() {
        return instant;
    }
}
