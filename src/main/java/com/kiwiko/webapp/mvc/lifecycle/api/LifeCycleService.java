package com.kiwiko.webapp.mvc.lifecycle.api;

public interface LifeCycleService {

    void startUp();

    void shutDown();

    void run(LifeCycleHook hook);
}
