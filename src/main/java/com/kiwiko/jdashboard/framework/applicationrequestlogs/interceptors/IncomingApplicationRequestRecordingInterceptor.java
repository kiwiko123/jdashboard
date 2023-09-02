package com.kiwiko.jdashboard.framework.applicationrequestlogs.interceptors;

import com.kiwiko.jdashboard.sessions.client.api.dto.Session;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLog;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLogService;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import com.kiwiko.jdashboard.webapp.framework.interceptors.internal.SessionRequestHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Objects;

public class IncomingApplicationRequestRecordingInterceptor implements RequestInterceptor {

    @Inject private IncomingApplicationRequestLogService incomingApplicationRequestLogService;
    @Inject private SessionRequestHelper sessionRequestHelper;

    @Override
    public void preRender(HttpServletRequest request, HttpServletResponse response, HandlerMethod method, ModelAndView modelAndView) throws Exception {
        String remoteHost = request.getRemoteHost();
        String ipAddress = request.getRemoteAddr();

        IncomingApplicationRequestLog incomingApplicationRequestLog = new IncomingApplicationRequestLog();

        incomingApplicationRequestLog.setUri(request.getRequestURI());
        incomingApplicationRequestLog.setIpAddress(ipAddress);
        incomingApplicationRequestLog.setRemoteHost(Objects.equals(ipAddress, remoteHost) ? null : remoteHost);
        incomingApplicationRequestLog.setRequestDate(Instant.now());
        sessionRequestHelper.getSessionFromRequest(request)
                .map(Session::getUserId)
                .ifPresent(incomingApplicationRequestLog::setUserId);

        incomingApplicationRequestLogService.create(incomingApplicationRequestLog);
    }
}
