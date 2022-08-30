package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;

public class EmitApplicationEventRequest {
    private ApplicationEvent event;
    private String operation;
    private boolean disableRequestSecurity = true;

    public ApplicationEvent getEvent() {
        return event;
    }

    public void setEvent(ApplicationEvent event) {
        this.event = event;
    }

    /**
     * @see EmitApplicationEventOperations
     */
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public boolean isDisableRequestSecurity() {
        return disableRequestSecurity;
    }

    public void setDisableRequestSecurity(boolean disableRequestSecurity) {
        this.disableRequestSecurity = disableRequestSecurity;
    }
}
