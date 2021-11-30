package com.kiwiko.webapp.notifications.internal.push;

import com.kiwiko.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfiguredBy(LoggingConfiguration.class)
public class NotificationPushServiceSubscriberConfiguration {

    @Bean
    public NotificationPushServiceSubscriber notificationPushServiceSubscriber() {
        return new NotificationPushServiceSubscriber();
    }
}
