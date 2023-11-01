package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JobSchedulerStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerStartupListener.class);

    @Inject private MultithreadedJobSchedulerDaemon multithreadedJobSchedulerDaemon;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("Starting the Job Scheduler daemon");
        multithreadedJobSchedulerDaemon.start();
    }
}
