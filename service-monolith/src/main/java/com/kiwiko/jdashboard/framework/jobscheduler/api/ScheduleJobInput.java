package com.kiwiko.jdashboard.framework.jobscheduler.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

@Builder
@Value
public class ScheduleJobInput {
    @NonNull String jobName;
    @NonNull JobFunction function;
    @Nullable Instant scheduledRunTime;
    @Nullable Duration timeout;
    @Nullable JobPriority jobPriority;
}
