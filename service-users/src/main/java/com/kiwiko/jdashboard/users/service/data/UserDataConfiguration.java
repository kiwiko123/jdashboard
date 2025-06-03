package com.kiwiko.jdashboard.users.service.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserDataConfiguration {

    @Bean
    UserDataAccessObject userDataAccessObject() {
        return new UserDataAccessObject();
    }

    @Bean
    UserCredentialDataAccessObject userCredentialDataAccessObject() {
        return new UserCredentialDataAccessObject();
    }
}
