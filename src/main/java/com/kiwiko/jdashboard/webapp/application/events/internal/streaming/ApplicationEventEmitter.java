package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

@Deprecated
public interface ApplicationEventEmitter {

    void emit(EmitApplicationEventRequest request);
}
