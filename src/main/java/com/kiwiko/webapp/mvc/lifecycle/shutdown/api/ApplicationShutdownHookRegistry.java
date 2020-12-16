package com.kiwiko.webapp.mvc.lifecycle.shutdown.api;

public interface ApplicationShutdownHookRegistry {

    void register(ApplicationShutdownHook shutdownHook);

    void shutdown();
}
