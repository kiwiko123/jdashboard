package com.kiwiko.webapp.mvc.lifecycle.api;

public interface LifeCycleHook {

    void run();

    default boolean isEnabled() {
        return true;
    }
}
