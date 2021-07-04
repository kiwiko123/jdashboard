package com.kiwiko.webapp.push.api;

import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;

public interface PushReceiver {

    /**
     * A push service requires a unique ID.
     * Consider using the fully qualified name of the application.
     *
     * @return the service's unique ID
     */
    String getServiceId();

    /**
     * Invoked whenever a push is received.
     *
     * @param parameters the parameters containing the pushed message
     */
    void onPushReceived(OnPushReceivedParameters parameters);
}
