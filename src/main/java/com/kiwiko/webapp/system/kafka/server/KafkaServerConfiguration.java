package com.kiwiko.webapp.system.kafka.server;

import com.kiwiko.webapp.system.kafka.server.api.KafkaServerService;
import com.kiwiko.webapp.system.kafka.server.internal.KafkaServerShutdownHook;
import com.kiwiko.webapp.system.kafka.server.internal.KafkaServerStartupHook;
import com.kiwiko.webapp.system.kafka.server.internal.ProcessBuilderKafkaServerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaServerConfiguration {

    @Bean
    public KafkaServerService kafkaServerService() {
        return new ProcessBuilderKafkaServerService();
    }

    @Bean
    public KafkaServerStartupHook kafkaServerStartupHook() {
        return new KafkaServerStartupHook();
    }

    @Bean
    public KafkaServerShutdownHook kafkaServerShutdownHook() {
        return new KafkaServerShutdownHook();
    }
}
