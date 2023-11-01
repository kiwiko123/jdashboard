package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import com.kiwiko.jdashboard.framework.jobscheduler.internal.dto.RunningJob;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class RunningJobsTracker {
    private final Map<String, RunningJob> runningJobsByJobName;

    public RunningJobsTracker() {
        runningJobsByJobName = new ConcurrentHashMap<>();
    }

    public void addRunningJob(RunningJob runningJob) {
        runningJobsByJobName.put(runningJob.getJobName(), runningJob);
    }

    public void removeRunningJob(String jobName) {
        runningJobsByJobName.remove(jobName);
    }

    public int getRunningJobsCount() {
        return runningJobsByJobName.size();
    }
}
