package com.kiwiko.webapp.application.events.queue;

import com.kiwiko.webapp.application.events.ApplicationEventConfiguration;
import com.kiwiko.webapp.application.events.queue.api.interfaces.ApplicationEventQueue;
import com.kiwiko.webapp.application.events.queue.internal.ApplicationEventServiceQueue;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfiguredBy(ApplicationEventConfiguration.class)
public class ApplicationEventQueueConfiguration {

    @Bean
    public ApplicationEventQueue applicationEventQueue() {
        return new ApplicationEventServiceQueue();
    }
}
