package com.kiwiko.webapp.users;

import com.kiwiko.webapp.mvc.security.authentication.api.PasswordService;
import com.kiwiko.webapp.users.internal.UserEntityPasswordService;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.internal.UserEntityMapper;
import com.kiwiko.webapp.users.internal.UserEntityService;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
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
