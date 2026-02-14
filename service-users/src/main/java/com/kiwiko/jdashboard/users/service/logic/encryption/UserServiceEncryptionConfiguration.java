package com.kiwiko.jdashboard.users.service.logic.encryption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserServiceEncryptionConfiguration {
    @Bean
    UserCredentialEncryptor userCredentialEncryptor() {
        return new BCryptUserCredentialEncryptor();
    }
}
