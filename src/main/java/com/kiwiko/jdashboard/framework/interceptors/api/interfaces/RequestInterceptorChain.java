package com.kiwiko.jdashboard.framework.interceptors.api.interfaces;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.interceptors.IncomingApplicationRequestRecordingInterceptor;
import com.kiwiko.jdashboard.framework.permissions.internal.UserPermissionCheckInterceptor;
import com.kiwiko.jdashboard.framework.ratelimiting.interceptors.RateLimiterInterceptor;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestErrorInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.AuthorizedServiceClientsInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.UserAuthCheckInterceptor;
import com.kiwiko.jdashboard.framework.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class RequestInterceptorChain {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptorChain.class);

    private Iterable<RequestInterceptor> interceptors;

    @Inject private CrossSiteRequestForgeryPreventionInterceptor crossSiteRequestForgeryPreventionInterceptor;
    @Inject private RateLimiterInterceptor rateLimiterInterceptor;
    @Inject private UserAuthCheckInterceptor userAuthCheckInterceptor;
    @Inject private AuthorizedServiceClientsInterceptor authorizedServiceClientsInterceptor;
    @Inject private UserPermissionCheckInterceptor userPermissionCheckInterceptor;
    @Inject private IncomingApplicationRequestRecordingInterceptor incomingApplicationRequestRecordingInterceptor;
    @Inject private RequestContextInterceptor requestContextInterceptor;
    @Inject private RequestErrorInterceptor requestErrorInterceptor;

    private List<RequestInterceptor> makeInterceptors() {
        List<RequestInterceptor> interceptors = new LinkedList<>();

        // Add interceptors here.
        interceptors.add(crossSiteRequestForgeryPreventionInterceptor);
        interceptors.add(rateLimiterInterceptor);
        interceptors.add(userAuthCheckInterceptor);
        interceptors.add(authorizedServiceClientsInterceptor);
        interceptors.add(userPermissionCheckInterceptor);
        interceptors.add(incomingApplicationRequestRecordingInterceptor);
        interceptors.add(requestContextInterceptor);
        interceptors.add(requestErrorInterceptor);

        return Collections.unmodifiableList(interceptors);
    }

    public synchronized Iterable<RequestInterceptor> getInterceptors() {
        if (interceptors == null) {
            LOGGER.info("Creating endpoint interceptor chain");
            interceptors = makeInterceptors();
        }

        return interceptors;
    }
}
