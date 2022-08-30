package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

public interface ApplicationEventEmitter {

    void emit(EmitApplicationEventRequest request);
}
