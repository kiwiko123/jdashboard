package com.kiwiko.jdashboard.users.client;

import com.kiwiko.jdashboard.users.client.api.interfaces.UserClient;
import com.kiwiko.jdashboard.users.client.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.users.client.impl.http.UserHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration {

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class, GsonJsonConfiguration.class})
    public UserClient userClient() {
        return new UserHttpClient();
    }

    @Bean
    public UserDtoMapper userDtoMapper() {
        return new UserDtoMapper();
    }
}
