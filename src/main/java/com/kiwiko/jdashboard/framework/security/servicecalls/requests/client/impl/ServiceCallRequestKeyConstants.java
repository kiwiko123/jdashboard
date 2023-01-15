package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl;

import java.time.Duration;

class ServiceCallRequestKeyConstants {
    public static final String SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER_NAME = "___JDASHBOARD_SERVICE_CLIENT";
    public static final Duration DEFAULT_REQUEST_KEY_EXPIRATION_DURATION = Duration.ofSeconds(30);
    public static final Duration MAX_CUSTOM_REQUEST_KEY_EXPIRATION_DURATION = Duration.ofDays(1);
    public static final String PROVISION_REQUEST_KEY_APPLICATION_EVENT_NAME = "jdashboard.framework.security.servicecalls.requests.keys.provisioned";
    
    private ServiceCallRequestKeyConstants() {}
}
