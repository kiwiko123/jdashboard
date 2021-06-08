package com.kiwiko.webapp.system.events.api.parameters;

import javax.annotation.Nullable;

/**
 * Query parameters for fetching {@link com.kiwiko.webapp.system.events.dto.ApplicationEvent}s from the database.
 * Specified parameters are "and"-ed together in the query.
 */
public class ApplicationEventQuery {
    private String eventType;
    private @Nullable String eventKey;

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
}
