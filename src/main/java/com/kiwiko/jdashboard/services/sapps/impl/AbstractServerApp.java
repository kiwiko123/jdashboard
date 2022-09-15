package com.kiwiko.jdashboard.services.sapps.impl;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.sapps.api.InvalidIdentifierException;
import com.kiwiko.jdashboard.services.sapps.api.ServerApp;
import com.kiwiko.jdashboard.services.sapps.api.ServerAppIdentifier;

import javax.inject.Inject;

public class AbstractServerApp implements ServerApp {

    @Inject private Logger logger;

    @Override
    public String id() {
        ServerAppIdentifier identifier = getClass().getAnnotation(ServerAppIdentifier.class);
        if (identifier == null) {
            throw new InvalidIdentifierException("No server app ID found; consider using @ServerAppIdentifier");
        }
        return identifier.value();
    }

    @Override
    public void onAppDidStart() {
        logger.debug("App did load");
    }

    @Override
    public void onAppWillExit() {
        logger.debug("App will exit");
    }
}
