package com.kiwiko.automation.actions.internal;

import com.kiwiko.automation.actions.api.ActionError;
import com.kiwiko.automation.actions.api.ActionService;
import com.kiwiko.automation.actions.api.ActionStatus;
import com.kiwiko.automation.actions.api.StatusType;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PollingActionService implements ActionService {

    private static final Map<String, ActionItem> actions = new HashMap<>();
    private final Thread executionThread;
    private final TemporalAmount pollFrequency = Duration.ofSeconds(5);

    public PollingActionService() {
        String threadName = String.format("%sThread", getClass().getName());
        executionThread = new Thread(this::run, threadName);
        executionThread.start();
    }

    @Override
    public void schedule(String id, Runnable runnable, Instant startTime) {
        if (!canAdd(id)) {
            throw new IllegalArgumentException(String.format("Action with id \"%s\" already exists", id));
        }

        ActionItem actionItem = new ActionItem(id, runnable, startTime, StatusType.WAITING);
        actions.put(id, actionItem);
    }

    @Override
    public void recur(String id, Runnable runnable, Instant startTime, TemporalUnit frequency) {
        if (!canAdd(id)) {
            throw new IllegalArgumentException(String.format("Action with id \"%s\" already exists", id));
        }

        ActionItem actionItem = new ActionItem(id, runnable, startTime, StatusType.WAITING, frequency);
        actions.put(id, actionItem);
    }

    @Override
    public Optional<ActionStatus> getStatus(String id) {
        return Optional.ofNullable(actions.get(id));
    }

    private boolean canAdd(String id) {
        return !actions.containsKey(id) || actions.get(id)
                .getStatusType()
                .isFinished();
    }

    private void run() {
        long sleepMs = Duration.from(pollFrequency).toMillis();
        while (executionThread.isAlive() && !executionThread.isInterrupted()) {
            startNewActions();
            cleanActions();

            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                executionThread.interrupt();
            }
        }
    }

    private void startNewActions() {
        actions.values().stream()
                .filter(actionItem -> actionItem.getStatusType() == StatusType.WAITING)
                .filter(actionItem -> Instant.now().isAfter(actionItem.getStartTime()))
                .forEach(this::startAction);
    }

    private void cleanActions() {
        actions.values().stream()
                .filter(actionItem -> actionItem.getStatusType() == StatusType.COMPLETE)
                .map(ActionStatus::getId)
                .forEach(actions::remove);
    }

    private void updateAction(ActionItem itemToUpdate, StatusType newStatusType) {
        ActionItem updatedItem = new ActionItem(itemToUpdate, newStatusType);
        actions.replace(itemToUpdate.getId(), updatedItem);
    }

    private void startAction(ActionItem actionItem) {
        Thread thread = new Thread(() -> runAction(actionItem), actionItem.getId());
        thread.start();
        if (actionItem.getStatusType() == StatusType.WAITING) {
            updateAction(actionItem, StatusType.RUNNING);
        }
    }

    private void runAction(ActionItem actionItem) {
        StatusType newStatusType = StatusType.COMPLETE;
        try {
            actionItem.run();
        } catch (ActionError e) {
            newStatusType = StatusType.FAILED;
        }
        postHandleAction(actionItem, newStatusType);
    }

    private void postHandleAction(ActionItem actionItem, StatusType newStatusType) {
        updateAction(actionItem, newStatusType);
        if (actionItem.isRecurring()) {
            Instant nextStartTime = actionItem.getStartTime().plus(1, actionItem.getFrequency());
            schedule(actionItem.getId(), actionItem.getRunnable(), nextStartTime);
        }
    }
}
