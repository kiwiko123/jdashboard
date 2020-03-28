package com.kiwiko.mvc.requests.internal.entities;

import com.kiwiko.persistence.entities.InMemoryDataEntity;

import javax.annotation.Nullable;
import java.time.Instant;

public class RequestContextEntity extends InMemoryDataEntity {

    private String uri;
    private Instant startInstant;
    private @Nullable Instant endInstant;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    public void setStartInstant(Instant startInstant) {
        this.startInstant = startInstant;
    }

    @Nullable
    public Instant getEndInstant() {
        return endInstant;
    }

    public void setEndInstant(@Nullable Instant endInstant) {
        this.endInstant = endInstant;
    }
}
