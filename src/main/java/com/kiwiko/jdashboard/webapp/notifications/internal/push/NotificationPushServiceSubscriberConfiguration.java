package com.kiwiko.jdashboard.webapp.notifications.internal.push;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfiguredBy(LoggingConfiguration.class)
public class NotificationPushServiceSubscriberConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public NotificationPushServiceSubscriber notificationPushServiceSubscriber() {
        return new NotificationPushServiceSubscriber();
    }
}
