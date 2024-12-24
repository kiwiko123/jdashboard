package com.kiwiko.jdashboard.framework.applicationrequestlogs.interceptors;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.IncomingApplicationRequestLogServiceConfiguration;
import com.kiwiko.jdashboard.webapp.framework.MvcConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationRequestLogInterceptorConfiguration {
    @Bean
    @ConfiguredBy({
            IncomingApplicationRequestLogServiceConfiguration.class,
            MvcConfiguration.class,
    })
    public IncomingApplicationRequestRecordingInterceptor incomingApplicationRequestInterceptor() {
        return new IncomingApplicationRequestRecordingInterceptor();
    }
}
