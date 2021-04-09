package com.kiwiko.clients.users;

import com.kiwiko.clients.users.api.UserClient;
import com.kiwiko.clients.users.impl.UserHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration {

    @Bean
    public UserClient userClient() {
        return new UserHttpClient();
    }
}
