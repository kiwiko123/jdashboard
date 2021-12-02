package com.kiwiko.jdashboard.webapp.mvc.json.gson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GsonJsonConfiguration.class)
public class GsonJsonConfiguration {

    @Bean
    public GsonProvider gsonProvider() {
        return new GsonProvider();
    }
}
