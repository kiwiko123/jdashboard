package com.kiwiko.webapp.mvc.persistence;

import com.kiwiko.webapp.mvc.persistence.impl.VersionConverterHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public VersionConverterHelper versionConverterHelper() {
        return new VersionConverterHelper();
    }
}
