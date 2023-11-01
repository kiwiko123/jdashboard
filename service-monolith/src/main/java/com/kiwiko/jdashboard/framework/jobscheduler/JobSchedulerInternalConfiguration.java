package com.kiwiko.jdashboard.framework.jobscheduler;

import com.kiwiko.jdashboard.framework.jobscheduler.internal.MultithreadedJobSchedulerDaemon;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.RunningJobsTracker;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.ScheduledJobsQueue;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class JobSchedulerInternalConfiguration {
    @Bean
    public MultithreadedJobSchedulerDaemon multithreadedJobSchedulerDaemon(
            ScheduledJobsQueue scheduledJobsQueue,
            RunningJobsTracker runningJobsTracker,
            @Value("${jdashboard.framework.jobscheduler.max-concurrent-jobs}") int maxConcurrentJobs) {
        return new MultithreadedJobSchedulerDaemon(scheduledJobsQueue, runningJobsTracker, maxConcurrentJobs);
    }

    @Bean
    public ScheduledJobsQueue scheduledJobsQueue() {
        return new ScheduledJobsQueue();
    }

    @Bean
    public RunningJobsTracker runningJobsTracker() {
        return new RunningJobsTracker();
    }
}
