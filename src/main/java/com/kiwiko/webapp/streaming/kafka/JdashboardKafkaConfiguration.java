package com.kiwiko.webapp.streaming.kafka;

import com.kiwiko.webapp.streaming.kafka.api.JdashboardKafkaProducer;
import com.kiwiko.webapp.streaming.kafka.internal.JdashboardKafkaTemplateProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashboardKafkaConfiguration {

    @Bean
    public JdashboardKafkaProducer jdashboardKafkaProducer() {
        return new JdashboardKafkaTemplateProducer();
    }
}
