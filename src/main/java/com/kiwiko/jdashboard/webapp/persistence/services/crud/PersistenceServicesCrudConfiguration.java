package com.kiwiko.jdashboard.webapp.persistence.services.crud;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.JsonConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PersistenceServicesCrudConfiguration.class)
public class PersistenceServicesCrudConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(TransactionConfiguration.class)
    public CreateReadUpdateDeleteExecutor createReadUpdateDeleteExecutor() {
        return new CreateReadUpdateDeleteExecutor();
    }

    @Bean
    @ConfiguredBy({TransactionConfiguration.class, JsonConfiguration.class, LoggingConfiguration.class})
    public EntityMerger entityMerger() {
        return new EntityMerger();
    }
}
