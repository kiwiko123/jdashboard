package com.kiwiko.jdashboard.webapp.persistence.data.cdc;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.framework.requests.RequestConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.DataEntityUpdateFetcher;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.TableRecordVersionDataEntityUpdateFetcher;
import com.kiwiko.jdashboard.services.tablerecordversions.PersistenceDataVersionsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ChangeDataCapturePersistenceConfiguration.class)
public class ChangeDataCapturePersistenceConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({RequestConfiguration.class, GsonJsonConfiguration.class, LoggingConfiguration.class, PersistenceDataVersionsConfiguration.class})
    public DataChangeCapturer dataChangeCapturer() {
        return new DataChangeCapturer();
    }

    @Bean
    @ConfiguredBy(PersistenceDataVersionsConfiguration.class)
    public DataEntityUpdateFetcher dataEntityUpdateFetcher() {
        return new TableRecordVersionDataEntityUpdateFetcher();
    }
}
