package com.kiwiko.jdashboard.userauth.service;

import com.kiwiko.jdashboard.sessions.client.SessionClientConfiguration;
import com.kiwiko.jdashboard.usercredentials.client.UserCredentialClientConfiguration;
import com.kiwiko.jdashboard.users.client.UserClientConfiguration;
import com.kiwiko.jdashboard.userauth.service.internal.UserCreator;
import com.kiwiko.jdashboard.userauth.service.internal.UserLoginAuthenticator;
import com.kiwiko.jdashboard.timeline.events.client.TimelineEventClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthConfiguration {

    @Bean
    @ConfiguredBy({
            UserClientConfiguration.class,
            UserCredentialClientConfiguration.class,
            SessionClientConfiguration.class,
            TimelineEventClientConfiguration.class,
    })
    public UserLoginAuthenticator userAuthenticator() {
        return new UserLoginAuthenticator();
    }

    @Bean
    @ConfiguredBy({UserClientConfiguration.class, UserCredentialClientConfiguration.class})
    public UserCreator userCreator() {
        return new UserCreator();
    }
}
