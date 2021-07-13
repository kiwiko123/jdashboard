package com.kiwiko.webapp.persistence.services.crud;

import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PersistenceServicesCrudConfiguration.class)
public class PersistenceServicesCrudConfiguration {

    @Bean
    public CreateReadUpdateDeleteExecutor createReadUpdateDeleteExecutor() {
        return new CreateReadUpdateDeleteExecutor();
    }
}
