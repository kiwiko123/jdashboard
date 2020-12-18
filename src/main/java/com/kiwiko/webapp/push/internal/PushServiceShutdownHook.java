package com.kiwiko.webapp.push.internal;

import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;

import javax.inject.Inject;

public class PushServiceShutdownHook implements ShutdownHook {

    @Inject private PushServiceSessionManager pushServiceSessionManager;

    @Override
    public void run() {
        pushServiceSessionManager.purge();
    }
}
