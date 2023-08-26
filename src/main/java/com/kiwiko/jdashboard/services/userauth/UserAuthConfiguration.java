package com.kiwiko.jdashboard.services.userauth;

import com.kiwiko.jdashboard.clients.sessions.SessionClientConfiguration;
import com.kiwiko.jdashboard.clients.usercredentials.UserCredentialClientConfiguration;
import com.kiwiko.jdashboard.clients.users.UserClientConfiguration;
import com.kiwiko.jdashboard.services.userauth.internal.UserCreator;
import com.kiwiko.jdashboard.services.userauth.internal.UserLoginAuthenticator;
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
