package com.kiwiko.automation.actions.api;

import java.time.Instant;
import java.util.Objects;

public class ActionStatus {

    private final String id;
    private final Instant startTime;
    private final StatusType statusType;

    public ActionStatus(String id, Instant startTime, StatusType statusType) {
        this.id = id;
        this.startTime = startTime;
        this.statusType = statusType;
    }

    public String getId() {
        return id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        ActionStatus otherActionStatus = (ActionStatus) other;
        return Objects.equals(id, otherActionStatus.id)
                && Objects.equals(startTime, otherActionStatus.startTime)
                && Objects.equals(statusType, otherActionStatus.statusType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, statusType);
    }
}
