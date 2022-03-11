package com.kiwiko.jdashboard.services.users;

import com.kiwiko.jdashboard.clients.users.UserClientConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.PasswordService;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import com.kiwiko.jdashboard.services.users.internal.UserEntityPasswordService;
import com.kiwiko.jdashboard.services.users.api.interfaces.UserService;
import com.kiwiko.jdashboard.services.users.internal.UserEntityMapper;
import com.kiwiko.jdashboard.services.users.internal.UserEntityService;
import com.kiwiko.jdashboard.services.users.internal.data.UserEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration implements JdashboardDependencyConfiguration {

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
