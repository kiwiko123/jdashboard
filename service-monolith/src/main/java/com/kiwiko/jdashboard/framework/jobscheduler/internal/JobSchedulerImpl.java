package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import com.kiwiko.jdashboard.framework.jobscheduler.api.JobPriority;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobScheduler;
import com.kiwiko.jdashboard.framework.jobscheduler.api.ScheduleJobInput;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.dto.ScheduledJob;

import javax.inject.Inject;
import java.time.Instant;

public class JobSchedulerImpl implements JobScheduler {
    @Inject private ScheduledJobsQueue scheduledJobsQueue;

    @Override
    public void queue(ScheduleJobInput input) {
        Instant now = Instant.now();

        ScheduledJob scheduledJob = ScheduledJob.builder()
                .jobName(input.getJobName())
                .function(input.getFunction())
                .scheduledRunTime(input.getScheduledRunTime() == null ? now : input.getScheduledRunTime())
                .jobPriority(input.getJobPriority() == null ? JobPriority.LOW : input.getJobPriority())
                .timeout(input.getTimeout())
                .createdTime(now)
                .build();

        scheduledJobsQueue.queue(scheduledJob);
    }
}
