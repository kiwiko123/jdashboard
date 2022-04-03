package com.kiwiko.jdashboard.clients.tablerecordversions;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.TableRecordVersionHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableRecordVersionClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(JdashboardApiClientConfiguration.class)
    public TableRecordVersionClient tableRecordVersionClient() {
        return new TableRecordVersionHttpClient();
    }
}