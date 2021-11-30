package com.kiwiko.webapp.mvc.security.authentication;

import com.kiwiko.webapp.mvc.security.authentication.internal.events.UserAuthenticationEventClient;
import com.kiwiko.webapp.mvc.security.authentication.internal.events.DirectServiceUserAuthenticationEventClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AuthenticationConfiguration.class)
public class AuthenticationConfiguration {

    @Bean
    public UserAuthenticationEventClient userAuthenticationEventClient() {
        return new DirectServiceUserAuthenticationEventClient();
    }


}
