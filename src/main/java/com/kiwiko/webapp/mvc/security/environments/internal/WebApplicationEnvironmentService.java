package com.kiwiko.webapp.mvc.security.environments.internal;

import com.kiwiko.webapp.mvc.security.environments.api.EnvironmentService;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentType;

public class WebApplicationEnvironmentService implements EnvironmentService {

    @Override
    public EnvironmentType getEnvironmentType() {
        // TODO
        return EnvironmentType.TEST;
    }
}
