package com.kiwiko.jdashboard.framework.interceptors.api.interfaces;

import com.kiwiko.jdashboard.framework.permissions.internal.UserPermissionCheckInterceptor;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.interceptors.RequestErrorInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.InternalServiceCheckInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.UserAuthCheckInterceptor;
import com.kiwiko.jdashboard.webapp.framework.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class EndpointInterceptorChain {

    private Iterable<EndpointInterceptor> interceptors;

    @Inject private Logger logger;
    @Inject private CrossSiteRequestForgeryPreventionInterceptor crossSiteRequestForgeryPreventionInterceptor;
    @Inject private UserAuthCheckInterceptor userAuthCheckInterceptor;
    @Inject private InternalServiceCheckInterceptor internalServiceCheckInterceptor;
    @Inject private UserPermissionCheckInterceptor userPermissionCheckInterceptor;
    @Inject private RequestContextInterceptor requestContextInterceptor;
    @Inject private RequestErrorInterceptor requestErrorInterceptor;

    private List<EndpointInterceptor> makeInterceptors() {
        List<EndpointInterceptor> interceptors = new LinkedList<>();

        // Add interceptors here.
        interceptors.add(crossSiteRequestForgeryPreventionInterceptor);
        interceptors.add(userAuthCheckInterceptor);
        interceptors.add(internalServiceCheckInterceptor);
        interceptors.add(userPermissionCheckInterceptor);
        interceptors.add(requestContextInterceptor);
        interceptors.add(requestErrorInterceptor);

        return Collections.unmodifiableList(interceptors);
    }

    public synchronized Iterable<EndpointInterceptor> getInterceptors() {
        if (interceptors == null) {
            logger.info("Creating endpoint interceptor chain");
            interceptors = makeInterceptors();
        }

        return interceptors;
    }
}
