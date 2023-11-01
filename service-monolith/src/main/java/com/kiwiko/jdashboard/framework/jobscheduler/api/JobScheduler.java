package com.kiwiko.jdashboard.framework.jobscheduler.api;

public interface JobScheduler {

    void queue(ScheduleJobInput input);
}
