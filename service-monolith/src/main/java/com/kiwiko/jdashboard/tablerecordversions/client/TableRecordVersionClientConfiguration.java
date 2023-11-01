package com.kiwiko.jdashboard.tablerecordversions.client;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.tablerecordversions.client.impl.http.TableRecordVersionHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableRecordVersionClientConfiguration {

    @Bean
    @ConfiguredBy({
            JdashboardApiClientConfiguration.class,
            GsonJsonConfiguration.class,
    })
    public TableRecordVersionClient tableRecordVersionClient() {
        return new TableRecordVersionHttpClient();
    }
}
