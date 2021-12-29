package com.kiwiko.jdashboard.webapp.framework.lifecycle.api;

public interface LifeCycleService {

    void startUp();

    void shutDown();

    void run(LifeCycleHook hook);
}
