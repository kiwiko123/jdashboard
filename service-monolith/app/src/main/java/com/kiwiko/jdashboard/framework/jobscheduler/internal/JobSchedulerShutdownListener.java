package com.kiwiko.jdashboard.framework.jobscheduler.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JobSchedulerShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerShutdownListener.class);

    @Inject private MultithreadedJobSchedulerDaemon multithreadedJobSchedulerDaemon;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Shutting down the Job Scheduler daemon");
        multithreadedJobSchedulerDaemon.shutdown();
    }
}
