package com.kiwiko.webapp.mvc.lifecycle.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHook;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleService;
import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;
import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

public class ApplicationLifeCycleService implements LifeCycleService {

    @Inject private LifeCycleHookRegistry lifeCycleHookRegistry;
    @Inject private Logger logger;

    @Override
    public void startUp() {
        Set<StartupHook> hooks = lifeCycleHookRegistry.getStartupHooks();
        sandboxRunHooks(hooks, "startup");

        // Startup hooks can be removed from memory after they've run.
        lifeCycleHookRegistry.clean();
    }

    @Override
    public void shutDown() {
        Set<ShutdownHook> hooks = lifeCycleHookRegistry.getShutdownHooks();
        sandboxRunHooks(hooks, "shutdown");
    }

    @Override
    public void run(LifeCycleHook hook) {
        hook.run();
    }

    private void sandboxRunHook(LifeCycleHook hook) {
        String hookName = hook.getClass().getName();
        logger.info(String.format("Running hook %s", hookName));
        try {
            hook.run();
        } catch (Exception e) {
            logger.error(String.format("Error running hook %s", hookName), e);
        }
    }

    private <Hook extends LifeCycleHook> void sandboxRunHooks(Collection<Hook> hooks, String type) {
        if (hooks.isEmpty()) {
            return;
        }
        int numberOfHooks = hooks.size();
        String hooksModifierWord = numberOfHooks == 1 ? "hook" : "hooks";
        logger.info(String.format("Beginning %d %s %s", numberOfHooks, type, hooksModifierWord));
        hooks.forEach(this::sandboxRunHook);
        logger.info(String.format("Completed %d %s", numberOfHooks, hooksModifierWord));
    }
}
