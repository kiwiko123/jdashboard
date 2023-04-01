package com.kiwiko.jdashboard.webapp.application.events.queue;

import com.kiwiko.jdashboard.webapp.application.events.ApplicationEventConfiguration;
import com.kiwiko.jdashboard.webapp.application.events.queue.api.interfaces.ApplicationEventQueue;
import com.kiwiko.jdashboard.webapp.application.events.queue.internal.ApplicationEventServiceQueue;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationEventQueueConfiguration {

    @Bean
    @ConfiguredBy(ApplicationEventConfiguration.class)
    public ApplicationEventQueue applicationEventQueue() {
        return new ApplicationEventServiceQueue();
    }
}
