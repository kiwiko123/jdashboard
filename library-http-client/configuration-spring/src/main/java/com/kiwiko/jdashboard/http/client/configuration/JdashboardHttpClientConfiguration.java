package com.kiwiko.jdashboard.http.client.configuration;

import com.kiwiko.jdashboard.http.client.JdashboardHttpClient;
import com.kiwiko.jdashboard.http.client.JdashboardHttpClientImpl;
import com.kiwiko.jdashboard.http.client.core.CoreHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashboardHttpClientConfiguration {
    @Bean
    JdashboardHttpClient jdashboardHttpClient() {
        return new JdashboardHttpClientImpl();
    }

    @Bean
    CoreHttpClient coreHttpClient() {
        return new CoreHttpClient();
    }
}
