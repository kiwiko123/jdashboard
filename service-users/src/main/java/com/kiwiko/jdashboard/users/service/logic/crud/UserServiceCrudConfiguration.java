package com.kiwiko.jdashboard.users.service.logic.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserServiceCrudConfiguration {

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    UserReader userReader() {
        return new UserReader();
    }

    @Bean
    UserWriter userWriter() {
        return new UserWriter();
    }

    @Bean
    UserCredentialEntityMapper userCredentialEntityMapper() {
        return new UserCredentialEntityMapper();
    }

    @Bean
    UserCredentialReader userCredentialReader() {
        return new UserCredentialReader();
    }

    @Bean
    UserCredentialWriter userCredentialWriter() {
        return new UserCredentialWriter();
    }
}
