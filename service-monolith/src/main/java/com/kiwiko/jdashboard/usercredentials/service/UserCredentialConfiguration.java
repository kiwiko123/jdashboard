package com.kiwiko.jdashboard.usercredentials.service;

import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.usercredentials.service.api.interfaces.UserCredentialService;
import com.kiwiko.jdashboard.usercredentials.service.internal.UserCredentialEntityMapper;
import com.kiwiko.jdashboard.usercredentials.service.internal.UserCredentialEntityService;
import com.kiwiko.jdashboard.usercredentials.service.internal.data.UserCredentialEntityDataAccessObject;
import com.kiwiko.jdashboard.usercredentials.service.internal.encryption.DefaultUserCredentialEncryptor;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCredentialConfiguration {

    @Bean
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public UserCredentialService userCredentialService() {
        return new UserCredentialEntityService();
    }

    @Bean
    public UserCredentialEntityMapper userCredentialEntityMapper() {
        return new UserCredentialEntityMapper();
    }

    @Bean
    public UserCredentialEntityDataAccessObject userCredentialEntityDataAccessObject() {
        return new UserCredentialEntityDataAccessObject();
    }

    @Bean
    public DefaultUserCredentialEncryptor defaultUserCredentialEncryptor() {
        return new DefaultUserCredentialEncryptor();
    }
}
