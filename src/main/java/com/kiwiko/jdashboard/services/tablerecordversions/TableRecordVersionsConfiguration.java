package com.kiwiko.jdashboard.services.tablerecordversions;

import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.TableRecordVersionEntityMapper;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.TableRecordVersionEntityService;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.data.TableRecordVersionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableRecordVersionsConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({TransactionConfiguration.class, PersistenceServicesCrudConfiguration.class})
    public TableRecordVersionService tableRecordVersionService() {
        return new TableRecordVersionEntityService();
    }

    @Bean
    public TableRecordVersionEntityMapper tableRecordVersionEntityMapper() {
        return new TableRecordVersionEntityMapper();
    }

    @Bean
    public TableRecordVersionEntityDataFetcher tableRecordVersionEntityDataFetcher() {
        return new TableRecordVersionEntityDataFetcher();
    }
}
