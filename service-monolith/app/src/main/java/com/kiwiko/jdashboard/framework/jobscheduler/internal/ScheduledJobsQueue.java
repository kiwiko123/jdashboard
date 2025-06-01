package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import com.kiwiko.jdashboard.framework.jobscheduler.internal.dto.ScheduledJob;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

@Singleton
public class ScheduledJobsQueue {
    private final PriorityBlockingQueue<ScheduledJob> jobQueue;

    public ScheduledJobsQueue() {
        // TODO consider job priority
        jobQueue = new PriorityBlockingQueue<>(1, Comparator.comparing(ScheduledJob::getScheduledRunTime));
    }

    public boolean isEmpty() {
        return jobQueue.isEmpty();
    }

    public Optional<ScheduledJob> peek() {
        return Optional.ofNullable(jobQueue.peek());
    }

    public void queue(ScheduledJob scheduledJob) {
        jobQueue.offer(scheduledJob);
    }

    public Optional<ScheduledJob> dequeue() {
        return Optional.ofNullable(jobQueue.poll());
    }
}
