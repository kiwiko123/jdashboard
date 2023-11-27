package com.kiwiko.jdashboard.webapp.persistence.data.cdc;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.framework.requests.RequestConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import com.kiwiko.jdashboard.tablerecordversions.service.TableRecordVersionsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ChangeDataCapturePersistenceConfiguration.class)
public class ChangeDataCapturePersistenceConfiguration {

    @Bean
    @ConfiguredBy({RequestConfiguration.class, GsonJsonConfiguration.class, LoggingConfiguration.class, TableRecordVersionsConfiguration.class})
    public DataChangeCapturer dataChangeCapturer() {
        return new DataChangeCapturer();
    }
}