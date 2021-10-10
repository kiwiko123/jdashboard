package com.kiwiko.webapp.middleware.interceptors.api.interfaces;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.interceptors.AuthenticationRequiredInterceptor;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestErrorInterceptor;
import com.kiwiko.webapp.mvc.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;

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
    @Inject private AuthenticationRequiredInterceptor authenticationRequiredInterceptor;
    @Inject private RequestContextInterceptor requestContextInterceptor;
    @Inject private RequestErrorInterceptor requestErrorInterceptor;

    private List<EndpointInterceptor> makeInterceptors() {
        List<EndpointInterceptor> interceptors = new LinkedList<>();

        // Add interceptors here.
        interceptors.add(crossSiteRequestForgeryPreventionInterceptor);
        interceptors.add(authenticationRequiredInterceptor);
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
