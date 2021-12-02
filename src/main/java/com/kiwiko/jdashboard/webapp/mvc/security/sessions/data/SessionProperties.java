package com.kiwiko.jdashboard.webapp.mvc.security.sessions.data;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

public class SessionProperties {
    public static final TemporalAmount AUTHENTICATION_COOKIE_TIME_TO_LIVE = Duration.ofDays(1);
    public static final String AUTHENTICATION_COOKIE_NAME = "COM_KIWIKO_JDASHBOARD_AUTH";
    public static final String REQUEST_CONTEXT_ID_SESSION_KEY = "/requestContextSessionId";
}
