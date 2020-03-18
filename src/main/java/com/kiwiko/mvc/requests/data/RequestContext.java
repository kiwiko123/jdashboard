package com.kiwiko.mvc.requests.data;

import com.kiwiko.persistence.identification.GeneratedLongIdentifiable;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class RequestContext extends GeneratedLongIdentifiable {

    private final String uri;
    private final Instant startInstant;
    private Optional<Instant> endInstant;

    public RequestContext(String uri, Instant startInstant, @Nullable Instant endInstant) {
        super();
        this.uri = uri;
        this.startInstant = startInstant;
        this.endInstant = Optional.ofNullable(endInstant);
    }

    public String getUri() {
        return uri;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    public Optional<Instant> getEndInstant() {
        return endInstant;
    }

    /**
     * @param endInstant the non-null end instant
     */
    public void setEndInstant(Instant endInstant) {
        this.endInstant = Optional.of(endInstant);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), getId().toString(), getStartInstant().toString());
    }
}
