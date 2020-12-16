package com.kiwiko.webapp.push.internal;

import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHook;

import javax.inject.Inject;

public class PushServiceShutdownHook implements ApplicationShutdownHook {

    @Inject private PushServiceSessionManager pushServiceSessionManager;

    @Override
    public void run() {
        pushServiceSessionManager.purge();
    }
}
