package com.kiwiko.webapp.streaming.kafka.internal.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.inject.Inject;
import java.util.Map;

@Configuration
@Import(KafkaClientConfiguration.class)
public class KafkaProducerConfiguration {

    @Inject private KafkaClientConfiguration kafkaClientConfiguration;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProperties = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClientConfiguration.getBootstrapServer(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProperties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
