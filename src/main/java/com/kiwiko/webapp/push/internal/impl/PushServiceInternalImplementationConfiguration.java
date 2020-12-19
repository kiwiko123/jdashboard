package com.kiwiko.webapp.push.internal.impl;

import com.kiwiko.webapp.push.api.PushServiceConfigurationCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class PushServiceInternalImplementationConfiguration {

    @Inject private PushServiceConfigurationCreator pushServiceConfigurationCreator;

    @Bean
    public PushNotificationDeliveryService pushNotificationDeliveryService() {
        return pushServiceConfigurationCreator.create(PushNotificationDeliveryService::new);
    }
}
