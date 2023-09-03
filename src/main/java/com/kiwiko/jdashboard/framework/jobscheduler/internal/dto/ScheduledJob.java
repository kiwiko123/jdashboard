package com.kiwiko.jdashboard.framework.jobscheduler.internal.dto;

import com.kiwiko.jdashboard.framework.jobscheduler.api.JobFunction;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobPriority;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

@Builder
@Value
public class ScheduledJob {
    @NonNull String jobName;
    @NonNull JobFunction function;
    @Nullable Duration timeout;
    @NonNull Instant createdTime;
    @NonNull Instant scheduledRunTime;
    @NonNull JobPriority jobPriority;
}
