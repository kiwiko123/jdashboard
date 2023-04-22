package com.kiwiko.jdashboard.framework.ratelimiting.interceptors;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLogService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class RateLimitRequestService {
    @Inject private IncomingApplicationRequestLogService incomingApplicationRequestLogService;

    public boolean isRateLimited(HttpServletRequest httpServletRequest) {
        return true;
    }
}
