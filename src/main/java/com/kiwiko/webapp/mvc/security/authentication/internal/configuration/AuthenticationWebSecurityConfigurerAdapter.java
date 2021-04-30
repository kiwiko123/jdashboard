package com.kiwiko.webapp.mvc.security.authentication.internal.configuration;

import com.kiwiko.webapp.mvc.security.environments.api.EnvironmentService;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.inject.Inject;

@Configuration
public class AuthenticationWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Inject
    private EnvironmentService environmentService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        EnvironmentType environmentType = environmentService.getEnvironmentType();
        switch (environmentType) {
            case TEST:
            case LOCAL:
                configureForTestEnvironment(httpSecurity);
                break;
        }
    }

    private void configureForTestEnvironment(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
