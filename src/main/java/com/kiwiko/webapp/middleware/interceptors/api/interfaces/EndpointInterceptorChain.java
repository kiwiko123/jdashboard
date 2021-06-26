package com.kiwiko.webapp.middleware.interceptors.api.interfaces;

import com.kiwiko.webapp.mvc.interceptors.AuthenticationRequiredInterceptor;

import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EndpointInterceptorChain {

    private List<EndpointInterceptor> interceptors;
    @Inject private AuthenticationRequiredInterceptor authenticationRequiredInterceptor;

    private List<EndpointInterceptor> makeInterceptors() {
        List<EndpointInterceptor> interceptors = new LinkedList<>();

        // Add interceptors here.
        interceptors.add(authenticationRequiredInterceptor);

        return Collections.unmodifiableList(interceptors);
    }

    public List<EndpointInterceptor> getInterceptors() {
        if (interceptors == null) {
            interceptors = makeInterceptors();
        }

        return interceptors;
    }
}
