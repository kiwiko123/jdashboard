package com.kiwiko.jdashboard.webapp.streaming.pushservice;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.PushServiceWebSocketConfiguration;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.TextWebSocketPushService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PushServiceConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(PushServiceWebSocketConfiguration.class)
    public PushService pushService() {
        return new TextWebSocketPushService();
    }
}
