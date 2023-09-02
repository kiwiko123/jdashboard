package com.kiwiko.jdashboard.users.service;

import com.kiwiko.jdashboard.users.client.UserClientConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.PasswordService;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import com.kiwiko.jdashboard.users.service.internal.UserEntityPasswordService;
import com.kiwiko.jdashboard.users.service.api.interfaces.UserService;
import com.kiwiko.jdashboard.users.service.internal.UserEntityMapper;
import com.kiwiko.jdashboard.users.service.internal.UserEntityService;
import com.kiwiko.jdashboard.users.service.internal.data.UserEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    @ConfiguredBy({UserClientConfiguration.class, PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public UserService userService() {
        return new UserEntityService();
    }

    @Bean
    public UserEntityDAO userEntityDAO() {
        return new UserEntityDAO();
    }

    @Bean
    public UserEntityMapper userEntityPropertyMapper() {
        return new UserEntityMapper();
    }

    @Bean
    public PasswordService passwordService() {
        return new UserEntityPasswordService();
    }
}
