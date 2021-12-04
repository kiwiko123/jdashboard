package com.kiwiko.jdashboard.webapp.http.client.impl;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalJdashboardHttpClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public HttpClientHelper httpClientHelper() {
        return new HttpClientHelper();
    }
}
