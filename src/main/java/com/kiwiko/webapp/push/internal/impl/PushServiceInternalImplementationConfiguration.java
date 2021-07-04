package com.kiwiko.webapp.push.internal.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PushServiceInternalImplementationConfiguration {

    @Bean
    public PushNotificationDeliveryService pushNotificationDeliveryService() {
        return new PushNotificationDeliveryService();
    }
}
