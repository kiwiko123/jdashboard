package com.kiwiko.jdashboard.timeline.events.client;

import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEventClient;
import com.kiwiko.jdashboard.timeline.events.client.impl.http.TimelineEventHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PUBLIC)
public class TimelineEventClientConfiguration {

    @Bean
    @ConfiguredBy(JdashboardApiClientConfiguration.class)
    public TimelineEventClient timelineEventClient() {
        return new TimelineEventHttpClient();
    }
}
