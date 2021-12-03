package com.kiwiko.jdashboard.webapp.clients.users;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.impl.di.UserServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration {

    @Bean
    public UserClient userClient() {
        return new UserServiceClient();
    }
}
