package com.kiwiko.jdashboard.webapp.middleware.datasources;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.jdashboard.webapp.framework.application.properties.PropertiesConfiguration;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@ConfiguredBy(PropertiesConfiguration.class)
public class DatasourceConfiguration {

    @Inject private JdashboardPropertyReader jdashboardPropertyReader;

    @Bean
    public DataSource dataSource() {
        String url = getRequiredProperty("database.url");
        String username = getRequiredProperty("database.username");
        String password = getOptionalProperty("database.password");

        DataSourceBuilder<HikariDataSource> builder = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(url)
                .username(username);

        if (password != null) {
            builder.password(password);
        }

        return builder.build();
    }

    private String getRequiredProperty(String name) {
        return Optional.ofNullable(jdashboardPropertyReader.get(name))
                .map(Property::getValue)
                .orElseThrow(() -> new DatasourceConfigurationException(String.format("No property with name %s found", name)));
    }

    @Nullable
    private String getOptionalProperty(String name) {
        return Optional.ofNullable(jdashboardPropertyReader.get(name))
                .map(Property::getValue)
                .orElse(null);
    }
}
