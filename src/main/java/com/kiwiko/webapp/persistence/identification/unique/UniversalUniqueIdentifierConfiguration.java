package com.kiwiko.webapp.persistence.identification.unique;

import com.kiwiko.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.webapp.persistence.identification.unique.internal.UniversalUniqueIdentifierEntityMapper;
import com.kiwiko.webapp.persistence.identification.unique.internal.UniversallyUniqueIdentifierEntityService;
import com.kiwiko.webapp.persistence.identification.unique.internal.UuidGenerator;
import com.kiwiko.webapp.persistence.identification.unique.internal.data.UniversalUniqueIdentifierEntityDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UniversalUniqueIdentifierConfiguration {

    @Bean
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
