package com.kiwiko.webapp.mvc.json.deserialization;

import com.kiwiko.webapp.mvc.json.deserialization.internal.resolvers.CustomRequestBodyArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MvcJsonDeserializationConfiguration.class)
public class MvcJsonDeserializationConfiguration {

    @Bean
    public CustomRequestBodyArgumentResolver customRequestBodyResolver() {
        return new CustomRequestBodyArgumentResolver();
    }
}
