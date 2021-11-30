package com.kiwiko.webapp.application.events.api.interfaces.parameters;

import com.kiwiko.webapp.application.events.api.dto.ApplicationEvent;

import javax.annotation.Nullable;

/**
 * Query parameters for fetching {@link ApplicationEvent}s from the database.
 * Specified parameters are "and"-ed together in the query.
 */
public class ApplicationEventQuery {
    private String eventType;
    private @Nullable String eventKey;
    private @Nullable Boolean isRemoved;

    public ApplicationEventQuery(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public ApplicationEventQuery setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    @Nullable
    public String getEventKey() {
        return eventKey;
    }

    public ApplicationEventQuery setEventKey(@Nullable String eventKey) {
        this.eventKey = eventKey;
        return this;
    }

    @Nullable
    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(@Nullable Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}
