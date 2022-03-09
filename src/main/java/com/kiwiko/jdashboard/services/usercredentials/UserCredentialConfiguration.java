package com.kiwiko.jdashboard.services.usercredentials;

import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.UserCredentialService;
import com.kiwiko.jdashboard.services.usercredentials.internal.UserCredentialEntityMapper;
import com.kiwiko.jdashboard.services.usercredentials.internal.UserCredentialEntityService;
import com.kiwiko.jdashboard.services.usercredentials.internal.data.UserCredentialEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCredentialConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(PersistenceServicesCrudConfiguration.class)
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
}
