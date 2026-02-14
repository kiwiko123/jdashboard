package com.kiwiko.jdashboard.users.service.logic;

import com.kiwiko.jdashboard.users.service.logic.authentication.UserPasswordAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserServiceLogicConfiguration {
    @Bean
    UserProvisioner userProvisioner() {
        return new UserProvisioner();
    }

    @Bean
    UserPasswordAuthenticator userPasswordAuthenticator() {
        return new UserPasswordAuthenticator();
    }
}
