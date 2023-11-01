package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

class JdashboardInternalHttpRequestProperties {
    public static final String SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER = "___JDASHBOARD_SERVICE_CLIENT";
    public static final String UUID_SERVICE_CLIENT_IDENTIFIER_METADATA_REFERENCE_KEY_NAME = "serviceClientId";
    public static final TemporalAmount INTERNAL_REQUEST_TOKEN_TTL = Duration.ofSeconds(5);
    public static final String EVENT_TYPE_NAME = "__JDASHBOARD_INTERNAL_SERVICE_REQUEST_TOKEN";
}
