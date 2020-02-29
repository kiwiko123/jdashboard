package com.kiwiko.automation.actions.internal;

import com.kiwiko.automation.actions.api.ActionError;
import com.kiwiko.automation.actions.api.ActionStatus;
import com.kiwiko.automation.actions.api.StatusType;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

class ActionItem extends ActionStatus {

    private final @Nullable TemporalUnit frequency;
    private final Runnable runnable;
    private @Nullable Instant endTime;

    /**
     * Standard constructor for a scheduled, one-time action.
     * @see #ActionItem(String, Runnable, Instant, StatusType, TemporalUnit).
     */
    public ActionItem(String id, Runnable runnable, Instant startTime, StatusType statusType) {
        this(id, runnable, startTime, statusType, null);
    }

    /**
     * Copy constructor accepting a new {@link StatusType}.
     *
     * @param actionItem the existing {@link ActionItem} whose fields will be copied.
     * @param statusType the new {@link StatusType}.
     */
    public ActionItem(ActionItem actionItem, StatusType statusType) {
        this(actionItem.getId(), actionItem.runnable, actionItem.getStartTime(), statusType, actionItem.getFrequency());
    }

    /**
     * Constructor for a scheduled, recurring action.
     * @param id the action's unique ID.
     * @param runnable the method that will be run.
     * @param startTime the time that the action will be run.
     * @param statusType the current status of the action.
     * @param frequency the unit of time of which the action will recur.
     */
    public ActionItem(
            String id,
            Runnable runnable,
            Instant startTime,
            StatusType statusType,
            @Nullable TemporalUnit frequency) {
        super(id, startTime, statusType);
        this.runnable = runnable;
        this.frequency = frequency;
    }

    public void run() throws ActionError {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new ActionError(e.getMessage());
        }
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public TemporalUnit getFrequency() {
        return frequency;
    }

    public boolean isRecurring() {
        return getFrequency() != null;
    }

    public Optional<Instant> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    public void setEndTime(@Nullable Instant endTime) {
        this.endTime = endTime;
    }
}
