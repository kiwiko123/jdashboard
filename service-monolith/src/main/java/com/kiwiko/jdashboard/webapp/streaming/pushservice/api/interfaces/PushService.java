package com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces;

import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions.PushException;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;

public interface PushService {

    void pushToClient(PushToClientParameters parameters) throws PushException;
}
