package com.kiwiko.jdashboard.services.sapps.api;

public interface ServerApp {

    String id();

    void onAppDidStart();

    void onAppWillExit();
}
