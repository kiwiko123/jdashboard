package com.kiwiko.webapp.middleware.datasources;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.webapp.mvc.application.properties.PropertiesConfiguration;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
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

        @SuppressWarnings("rawtypes")
        DataSourceBuilder builder = DataSourceBuilder.create()
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