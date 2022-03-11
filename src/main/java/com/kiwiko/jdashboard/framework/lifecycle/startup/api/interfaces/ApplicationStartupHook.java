package com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces;

public interface ApplicationStartupHook {

    void run() throws ApplicationStartupHookException;
}
