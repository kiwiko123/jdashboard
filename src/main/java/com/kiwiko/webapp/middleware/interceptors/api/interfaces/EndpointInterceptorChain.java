package com.kiwiko.webapp.middleware.interceptors.api.interfaces;

import com.kiwiko.webapp.mvc.interceptors.AuthenticationRequiredInterceptor;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestContextInterceptor;
import com.kiwiko.webapp.mvc.requests.internal.interceptors.RequestErrorInterceptor;

import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EndpointInterceptorChain {

    private List<EndpointInterceptor> interceptors;
    @Inject private AuthenticationRequiredInterceptor authenticationRequiredInterceptor;
    @Inject private RequestContextInterceptor requestContextInterceptor;
    @Inject private RequestErrorInterceptor requestErrorInterceptor;

    private List<EndpointInterceptor> makeInterceptors() {
        List<EndpointInterceptor> interceptors = new LinkedList<>();

        // Add interceptors here.
        interceptors.add(authenticationRequiredInterceptor);
        interceptors.add(requestContextInterceptor);
        interceptors.add(requestErrorInterceptor);

        return Collections.unmodifiableList(interceptors);
    }

    public List<EndpointInterceptor> getInterceptors() {
        if (interceptors == null) {
            interceptors = makeInterceptors();
        }

        return interceptors;
    }
}
