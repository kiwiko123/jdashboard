package com.kiwiko.jdashboard.webapp.http.client.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalJdashboardHttpClientConfiguration {

    @Bean
    public HttpClientHelper httpClientHelper() {
        return new HttpClientHelper();
    }
}
