package com.kiwiko.jdashboard.webapp.persistence.identification.unique;

import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.UniversalUniqueIdentifierEntityMapper;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.UniversallyUniqueIdentifierEntityService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.UuidGenerator;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.data.UniversalUniqueIdentifierEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UniversalUniqueIdentifierConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class})
    public UniqueIdentifierService uniqueIdentifierService() {
        return new UniversallyUniqueIdentifierEntityService();
    }

    @Bean
    public UniversalUniqueIdentifierEntityDataFetcher universalUniqueIdentifierEntityDataFetcher() {
        return new UniversalUniqueIdentifierEntityDataFetcher();
    }

    @Bean
    public UniversalUniqueIdentifierEntityMapper universalUniqueIdentifierEntityMapper() {
        return new UniversalUniqueIdentifierEntityMapper();
    }

    @Bean
    public UuidGenerator uuidGenerator() {
        return new UuidGenerator();
    }
}
