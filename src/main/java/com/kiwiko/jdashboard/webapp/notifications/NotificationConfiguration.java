package com.kiwiko.jdashboard.webapp.notifications;

import com.kiwiko.jdashboard.webapp.notifications.api.NotificationService;
import com.kiwiko.jdashboard.webapp.notifications.internal.NotificationEntityMapper;
import com.kiwiko.jdashboard.webapp.notifications.internal.NotificationEntityService;
import com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess.NotificationEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationService notificationService() {
        return new NotificationEntityService();
    }

    @Bean
    public NotificationEntityDAO notificationEntityDAO() {
        return new NotificationEntityDAO();
    }

    @Bean
    public NotificationEntityMapper notificationEntityMapper() {
        return new NotificationEntityMapper();
    }
}
