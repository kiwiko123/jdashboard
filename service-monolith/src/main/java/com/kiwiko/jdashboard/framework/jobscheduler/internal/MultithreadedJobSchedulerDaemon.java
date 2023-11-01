package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import com.kiwiko.jdashboard.framework.jobscheduler.api.JobFunctionInput;
import com.kiwiko.jdashboard.framework.jobscheduler.api.JobFunctionOutput;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.dto.RunningJob;
import com.kiwiko.jdashboard.framework.jobscheduler.internal.dto.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class MultithreadedJobSchedulerDaemon {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultithreadedJobSchedulerDaemon.class);

    private final ScheduledJobsQueue scheduledJobsQueue;
    private final RunningJobsTracker runningJobsTracker;
    private final int maxConcurrentJobs;

    private final ExecutorService executorService;
    private final ScheduledExecutorService schedulerExecutorService;
    private boolean isStarted;

    @Inject
    public MultithreadedJobSchedulerDaemon(
            ScheduledJobsQueue scheduledJobsQueue,
            RunningJobsTracker runningJobsTracker,
            int maxConcurrentJobs) {
        this.scheduledJobsQueue = scheduledJobsQueue;
        this.runningJobsTracker = runningJobsTracker;
        this.maxConcurrentJobs = maxConcurrentJobs;
        executorService = Executors.newCachedThreadPool();
        schedulerExecutorService = Executors.newScheduledThreadPool(1);
        isStarted = false;
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            spawnSchedulerThread();
        }
    }

    public void shutdown() {
        executorService.shutdown();
        schedulerExecutorService.shutdown();
    }

    private boolean isShutdown() {
        return executorService.isShutdown() && schedulerExecutorService.isShutdown();
    }

    private void spawnSchedulerThread() {
        CompletableFuture.supplyAsync(this::runNextScheduledJobs, schedulerExecutorService)
                .thenAccept((isActive) -> {
                    if (!isActive) {
                        return;
                    }
                    schedulerExecutorService.schedule(this::spawnSchedulerThread, 10, TimeUnit.SECONDS);
                });
    }

    private boolean runNextScheduledJobs() {
        if (isShutdown()) {
            return false;
        }

        runNextScheduledJobsAtInterval();
        return true;
    }

    private void runNextScheduledJobsAtInterval() {
        if (scheduledJobsQueue.isEmpty()) {
            return;
        }

        ScheduledJob nextJob = scheduledJobsQueue.peek().orElse(null);
        while (canRunNextJob(nextJob)) {
            final ScheduledJob jobToRun = scheduledJobsQueue.dequeue()
                    .orElseThrow(() -> new IllegalStateException("There are no jobs in the queue"));
            final JobFunctionInput jobFunctionInput = new JobFunctionInput();

            CompletableFuture.supplyAsync(
                    () -> runJobFunction(jobToRun, jobFunctionInput),
                    executorService)
                    .whenComplete((jobFunctionOutput, exception) -> handleJobCompletion(jobToRun, jobFunctionOutput, exception));

            RunningJob runningJob = RunningJob.builder()
                    .jobName(jobToRun.getJobName())
                    .runStartTime(Instant.now())
                    .build();
            runningJobsTracker.addRunningJob(runningJob);

            nextJob = scheduledJobsQueue.peek().orElse(null);
        }
    }

    private boolean canRunNextJob(@Nullable ScheduledJob nextJob) {
        if (nextJob == null) {
            return false;
        }

        if (runningJobsTracker.getRunningJobsCount() >= maxConcurrentJobs) {
            LOGGER.debug("Cannot run job at this time {} because the maximum number of concurrently running jobs has been reached", nextJob.getJobName());
            return false;
        }

        return Instant.now().isAfter(nextJob.getScheduledRunTime());
    }

    private JobFunctionOutput runJobFunction(ScheduledJob jobToRun, JobFunctionInput jobFunctionInput) {
        LOGGER.info("Running job: {}", jobToRun.getJobName());

        try {
            return jobToRun.getFunction().run(jobFunctionInput);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private void handleJobCompletion(ScheduledJob jobToRun, JobFunctionOutput jobFunctionOutput, @Nullable Throwable exception) {
        if (exception == null) {
            LOGGER.info("Successfully completed job \"{}\"", jobToRun.getJobName());
        } else {
            LOGGER.error("Scheduled job \"{}\" threw an unexpected error", jobToRun.getJobName(), exception);
        }

        runningJobsTracker.removeRunningJob(jobToRun.getJobName());
    }
}
