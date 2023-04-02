package com.kiwiko.jdashboard.framework.lifecycle.startup.internal;

import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHookException;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;
import com.kiwiko.jdashboard.framework.lifecycle.startup.registry.ApplicationStartupChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

public class ApplicationStartupService implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupService.class);

    @Inject private ApplicationStartupChain applicationStartupChain;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        LOGGER.info("Starting up application...");
        sandboxRunHooks();
    }

    private void sandboxRunHooks() {
        applicationStartupChain.getStartupHooks().forEach(this::sandboxRunHook);
    }

    private void sandboxRunHook(ApplicationStartupHook hook) {
        LOGGER.info("Running startup hook {}", hook.getClass().getName());
        long startTimeMs = System.currentTimeMillis();
        Long endTimeMs = null;

        try {
            hook.run();
            endTimeMs = System.currentTimeMillis();
        } catch (ApplicationStartupHookException e) {
            LOGGER.error("Error running startup hook {}", hook.getClass(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error running startup hook {}", hook.getClass(), e);
        }

        if (endTimeMs == null) {
            LOGGER.info("Completed startup hook {}", hook.getClass().getName());
        } else {
            Instant startTime = Instant.ofEpochMilli(startTimeMs);
            Instant endTime = Instant.ofEpochMilli(endTimeMs);
            Duration hookRunDuration = Duration.between(startTime, endTime);
            LOGGER.info(
                    "Completed startup hook {} in {} milliseconds",
                    hook.getClass().getName(),
                    hookRunDuration.toMillis());
        }
    }
}
