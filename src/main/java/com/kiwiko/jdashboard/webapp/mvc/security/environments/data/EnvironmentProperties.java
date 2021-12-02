package com.kiwiko.jdashboard.webapp.mvc.security.environments.data;

import java.net.URI;

public class EnvironmentProperties {
    public static final URI LOCAL_SERVER_URI = URI.create("http://localhost:8080");
    private static final String CROSS_ORIGIN_DEV_URL = "http://localhost:3000";

    public static final String CROSS_ORIGIN_URL = CROSS_ORIGIN_DEV_URL;
}
