package com.kiwiko.mvc.security.environments.api;

import com.kiwiko.mvc.security.environments.data.EnvironmentType;

public interface EnvironmentService {

    // TODO make this dynamic?
    String CROSS_ORIGIN_DEV_URL = "http://localhost:3000";

    EnvironmentType getEnvironmentType();
}
