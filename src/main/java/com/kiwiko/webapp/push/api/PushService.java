package com.kiwiko.webapp.push.api;

import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;

public interface PushService {

    String getServiceId();

    boolean shouldPushToClient(PushToClientParameters parameters);

    void pushToClient(PushToClientParameters parameters) throws ClientUnreachablePushException;

    void onPushReceived(OnPushReceivedParameters parameters);
}
