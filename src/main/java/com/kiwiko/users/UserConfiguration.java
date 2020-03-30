package com.kiwiko.users;

import com.kiwiko.users.api.UserService;
import com.kiwiko.users.internal.UserEntityPropertyMapper;
import com.kiwiko.users.internal.UserEntityService;
import com.kiwiko.users.internal.dataAccess.UserEntityDAO;
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
    public UserEntityPropertyMapper userEntityPropertyMapper() {
        return new UserEntityPropertyMapper();
    }
}
