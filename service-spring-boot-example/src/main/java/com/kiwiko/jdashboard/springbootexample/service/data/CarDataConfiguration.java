package com.kiwiko.jdashboard.springbootexample.service.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarDataConfiguration {

    @Bean
    public CarDataAccessObject carDataAccessObject() {
        return new CarDataAccessObject();
    }
}
