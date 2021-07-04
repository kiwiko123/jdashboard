package com.kiwiko.webapp.push.api;

import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;

public interface PushService {

    String getServiceId();

    void pushToClient(PushToClientParameters parameters) throws ClientUnreachablePushException;
}
