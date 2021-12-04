package com.kiwiko.jdashboard.webapp.clients.users;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.webapp.clients.users.impl.di.UserServiceClient;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.users.UserConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration {

    @Bean
    @ConfiguredBy(UserConfiguration.class)
    public UserClient userClient() {
        return new UserServiceClient();
    }

    @Bean
    public UserDtoMapper userDtoMapper() {
        return new UserDtoMapper();
    }
}
