package com.kiwiko.webapp.http.client;

import com.kiwiko.library.http.client.api.AsynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.SynchronousHttpRequestClient;
import com.kiwiko.library.http.client.internal.HttpNetAsynchronousRequestClient;
import com.kiwiko.library.http.client.internal.HttpNetSynchronousRequestClient;
import com.kiwiko.webapp.http.client.impl.JdashboardHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashboardHttpClientConfiguration {

    @Bean
    public JdashboardHttpClient jdashboardHttpClient() {
        return new JdashboardHttpClient();
    }

    @Bean
    public SynchronousHttpRequestClient synchronousHttpRequestClient() {
        return new HttpNetSynchronousRequestClient();
    }

    @Bean
    public AsynchronousHttpRequestClient asynchronousHttpRequestClient() {
        return new HttpNetAsynchronousRequestClient();
    }
}
