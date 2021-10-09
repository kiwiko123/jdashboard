package com.kiwiko.webapp.streaming.kafka.playground;

import org.springframework.kafka.core.KafkaTemplate;

import javax.inject.Inject;

public class KafkaProducerTemplate {

    @Inject private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send("jdashboardTestTopic1", message);
    }
}
