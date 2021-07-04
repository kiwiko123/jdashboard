package com.kiwiko.webapp.mvc.security.environments.internal;

import com.kiwiko.webapp.mvc.security.environments.api.EnvironmentService;
import com.kiwiko.webapp.mvc.security.environments.api.errors.UnknownEnvironmentException;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentType;

import java.net.URI;

public class WebApplicationEnvironmentService implements EnvironmentService {

    @Override
    public EnvironmentType getEnvironmentType() {
        // TODO
        return EnvironmentType.LOCAL;
    }

    @Override
    public URI getServerURI() throws UnknownEnvironmentException {
        switch (getEnvironmentType()) {
            case LOCAL:
                return EnvironmentProperties.LOCAL_SERVER_URI;
            default:
                throw new UnknownEnvironmentException();
        }
    }
}
