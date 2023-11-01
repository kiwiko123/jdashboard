package com.kiwiko.jdashboard.framework.jobscheduler.internal.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class RunningJob {
    @NonNull String jobName;
    @NonNull Instant runStartTime;
}
