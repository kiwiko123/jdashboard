package com.kiwiko.webapp.persistence.data.versions;

import com.kiwiko.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import com.kiwiko.webapp.persistence.data.versions.internal.TableRecordVersionEntityMapper;
import com.kiwiko.webapp.persistence.data.versions.internal.TableRecordVersionEntityService;
import com.kiwiko.webapp.persistence.data.versions.internal.data.TableRecordVersionEntityDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PersistenceDataVersionsConfiguration.class)
public class PersistenceDataVersionsConfiguration {

    @Bean
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
