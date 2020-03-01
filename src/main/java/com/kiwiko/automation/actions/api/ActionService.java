package com.kiwiko.automation.actions.api;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

public interface ActionService {

    void schedule(String id, Runnable runnable, Instant startTime);

    void recur(String id, Runnable runnable, Instant startTime, TemporalUnit frequency);

    Optional<ActionStatus> getStatus(String id);
}
