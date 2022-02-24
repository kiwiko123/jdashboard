package com.kiwiko.jdashboard.framework.lifecycle.startup.internal;

import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHookException;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;
import com.kiwiko.jdashboard.framework.lifecycle.startup.registry.ApplicationStartupChain;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;

public class ApplicationStartupService implements ApplicationListener<ApplicationStartedEvent> {

    @Inject private Logger logger;
    @Inject private ApplicationStartupChain applicationStartupChain;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        logger.info("Starting up application");
        sandboxRunHooks();
    }

    private void sandboxRunHooks() {
        applicationStartupChain.getStartupHooks().forEach(this::sandboxRunHook);
    }

    private void sandboxRunHook(ApplicationStartupHook hook) {
        logger.info(String.format("Running startup hook %s", hook.getClass().getName()));

        try {
            hook.run();
        } catch (ApplicationStartupHookException e) {
            logger.error(String.format("Error running startup hook %s", hook.getClass()), e);
        } catch (Exception e) {
            logger.error(String.format("Unexpected error running startup hook %s", hook.getClass()), e);
        }

        logger.info(String.format("Completed startup hook %s", hook.getClass().getName()));
    }
}
