package com.kiwiko.webapp.streaming.kafka.internal.configuration;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.webapp.mvc.application.properties.PropertiesConfiguration;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyConstants;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.webapp.streaming.kafka.api.exceptions.JdashboardKafkaRuntimeException;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaAdmin;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@Configuration
@Import(PropertiesConfiguration.class)
public class KafkaClientConfiguration {

    @Inject private PropertiesConfiguration propertiesConfiguration;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServer());
        return new KafkaAdmin(configs);
    }

    JdashboardPropertyReader jdashboardPropertyReader() {
        return propertiesConfiguration.jdashboardPropertyFileReader();
    }

    String getBootstrapServer() throws JdashboardKafkaRuntimeException {
        return getProperty(JdashboardPropertyConstants.KAFKA_BOOTSTRAP_SERVER);
    }

    String getStandardGroupId() throws JdashboardKafkaRuntimeException {
        return getProperty(JdashboardPropertyConstants.KAFKA_GROUP_ID);
    }

    private String getProperty(String name) {
        return Optional.ofNullable(jdashboardPropertyReader().store(name))
                .map(Property::getValue)
                .orElseThrow(() -> new JdashboardKafkaRuntimeException(String.format("Error reading property %s from configuration file", name)));
    }
}
