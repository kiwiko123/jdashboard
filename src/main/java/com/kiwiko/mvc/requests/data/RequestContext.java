package com.kiwiko.mvc.requests.data;

import com.kiwiko.persistence.identification.GeneratedLongIdentifiable;

import javax.annotation.Nullable;
import java.time.Instant;

public class RequestContext extends GeneratedLongIdentifiable {

    private final String uri;
    private final Instant startInstant;
    private @Nullable Instant endInstant;

    public RequestContext(String uri, Instant startInstant, Instant endInstant) {
        super();
        this.uri = uri;
        this.startInstant = startInstant;
        this.endInstant = endInstant;
    }

    public String getUri() {
        return uri;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    @Nullable
    public Instant getEndInstant() {
        return endInstant;
    }

    public void setEndInstant(Instant endInstant) {
        this.endInstant = endInstant;
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), getId().toString(), getStartInstant().toString());
    }
}
