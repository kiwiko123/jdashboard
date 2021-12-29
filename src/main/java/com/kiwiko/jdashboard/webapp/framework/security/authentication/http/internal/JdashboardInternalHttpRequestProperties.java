package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

class JdashboardInternalHttpRequestProperties {
    public static final int HEADER_NAME_TOKEN_LENGTH = 16;
    public static final int HEADER_VALUE_TOKEN_LENGTH = 64;
    public static final String INTERNAL_REQUEST_HEADER_PREFIX = "___JDASHBOARD_INTERNAL_SERVICE_REQUEST";
    public static final TemporalAmount INTERNAL_REQUEST_TOKEN_TTL = Duration.ofSeconds(5);
    static final String EVENT_TYPE_NAME = "__JDASHBOARD_INTERNAL_SERVICE_REQUEST_TOKEN";
}
