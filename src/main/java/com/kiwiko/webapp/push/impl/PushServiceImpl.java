package com.kiwiko.webapp.push.impl;

import com.kiwiko.webapp.push.api.PushReceiver;

import java.util.Optional;

public abstract class PushServiceImpl extends TextWebSocketPushService {

    protected Optional<PushReceiver> getPushReceiver() {
        return Optional.empty();
    }
}
