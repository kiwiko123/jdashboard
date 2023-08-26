package com.kiwiko.jdashboard.timeline.events.service;

import com.kiwiko.jdashboard.timeline.events.service.internal.TimelineEventService;
import com.kiwiko.jdashboard.timeline.events.service.internal.data.TimelineEventEntityDataAccessObject;
import com.kiwiko.jdashboard.timeline.events.service.internal.data.TimelineEventEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class TimelineEventServiceInternalConfiguration {

    @Bean
    public TimelineEventEntityDataAccessObject timelineEventEntityDataAccessObject() {
        return new TimelineEventEntityDataAccessObject();
    }

    @Bean
    public TimelineEventEntityMapper timelineEventEntityMapper() {
        return new TimelineEventEntityMapper();
    }

    @Bean
    public TimelineEventService timelineEventService() {
        return new TimelineEventService();
    }
}
