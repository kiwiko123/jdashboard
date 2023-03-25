package com.kiwiko.jdashboard.webapp.framework.security.authentication.http;

import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ServiceCallRequestKeyProvisionerClientConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.ServiceRequestKeyServiceExternalConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal.JdashboardInternalHttpRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpAuthenticationConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({
            ServiceCallRequestKeyProvisionerClientConfiguration.class,
            ServiceRequestKeyServiceExternalConfiguration.class,
    })
    public InternalHttpRequestValidator internalHttpRequestValidator() {
        return new JdashboardInternalHttpRequestValidator();
    }
}
