package com.kiwiko.webapp.streaming.pushservice.api.interfaces;

import com.kiwiko.webapp.streaming.pushservice.api.interfaces.parameters.OnPushReceivedParameters;

public interface PushServiceSubscriber {

    void onPushReceived(OnPushReceivedParameters parameters);
}
