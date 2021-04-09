package com.kiwiko.webapp.mvc.security.environments.api;

import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentType;

import java.net.URI;

public interface EnvironmentService {

    EnvironmentType getEnvironmentType();

    URI getServerURI();
}
