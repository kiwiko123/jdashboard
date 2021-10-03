package com.kiwiko.webapp.system.events.api.dto;

import com.kiwiko.library.persistence.data.api.interfaces.DataEntityDTO;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class ApplicationEvent extends DataEntityDTO {

    public static Builder newBuilder(String eventType) {
        return new Builder(eventType);
    }

    private String eventType;
    private @Nullable String eventKey;
    private @Nullable String metadata;
    private boolean isRemoved;
    private Instant createdDate;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Optional<String> getEventKey() {
        return Optional.ofNullable(eventKey);
    }

    public void setEventKey(@Nullable String eventKey) {
        this.eventKey = eventKey;
    }

    public Optional<String> getMetadata() {
        return Optional.ofNullable(metadata);
    }

    public void setMetadata(@Nullable String metadata) {
        this.metadata = metadata;
    }

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public final ApplicationEvent event;

        public Builder(String eventType) {
            event = new ApplicationEvent();
            event.setEventType(eventType);
        }

        public Builder setEventKey(String eventKey) {
            event.setEventKey(eventKey);
            return this;
        }

        public Builder setMetadata(String metadata) {
            event.setMetadata(metadata);
            return this;
        }

        public Builder setIsRemoved(boolean isRemoved) {
            event.isRemoved = isRemoved;
            return this;
        }

        public ApplicationEvent build() {
            return event;
        }
    }
}
