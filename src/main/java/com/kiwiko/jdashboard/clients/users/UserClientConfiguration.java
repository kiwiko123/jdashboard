package com.kiwiko.jdashboard.clients.users;

import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.clients.users.impl.http.UserHttpClient;
import com.kiwiko.jdashboard.tools.httpclient.impl.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfiguration implements JdashboardDependencyConfiguration {

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
