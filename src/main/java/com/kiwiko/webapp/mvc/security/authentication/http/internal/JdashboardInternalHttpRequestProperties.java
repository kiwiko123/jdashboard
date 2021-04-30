package com.kiwiko.webapp.mvc.security.authentication.http.internal;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

class JdashboardInternalHttpRequestProperties {
    public static final int HEADER_NAME_TOKEN_LENGTH = 16;
    public static final int HEADER_VALUE_TOKEN_LENGTH = 64;
    public static final String INTERNAL_REQUEST_HEADER_PREFIX = "___JDASHBOARD_INTERNAL_SERVICE_REQUEST";
    public static final TemporalAmount INTERNAL_REQUEST_TOKEN_TTL = Duration.ofMinutes(5);
}
