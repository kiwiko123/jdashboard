package com.kiwiko.jdashboard.webapp.notifications.internal.push;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationPushServiceSubscriberConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public NotificationPushServiceSubscriber notificationPushServiceSubscriber() {
        return new NotificationPushServiceSubscriber();
    }
}
