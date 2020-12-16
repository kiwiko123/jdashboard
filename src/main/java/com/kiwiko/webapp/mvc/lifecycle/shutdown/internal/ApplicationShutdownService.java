package com.kiwiko.webapp.mvc.lifecycle.shutdown.internal;

import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ShutdownService;

import javax.inject.Inject;

public class ApplicationShutdownService implements ShutdownService {

    @Inject private ApplicationShutdownHookRegistry applicationShutdownHookRegistry;

    @Override
    public void shutdown() {
        applicationShutdownHookRegistry.shutdown();
    }
}
