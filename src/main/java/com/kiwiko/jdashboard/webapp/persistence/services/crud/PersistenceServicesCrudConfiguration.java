package com.kiwiko.jdashboard.webapp.persistence.services.crud;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PersistenceServicesCrudConfiguration.class)
public class PersistenceServicesCrudConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public CreateReadUpdateDeleteExecutor createReadUpdateDeleteExecutor() {
        return new CreateReadUpdateDeleteExecutor();
    }

    @Bean
    public EntityMerger entityMerger() {
        return new EntityMerger();
    }
}
