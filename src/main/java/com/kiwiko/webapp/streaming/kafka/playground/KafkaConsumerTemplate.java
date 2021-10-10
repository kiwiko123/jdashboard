package com.kiwiko.webapp.streaming.kafka.playground;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class KafkaConsumerTemplate {

    @Inject private Logger logger;

    @KafkaListener(topics = "jdashboardTestTopic1")
    public void listen(String message) {
        logger.info(String.format("Received Kafka message \"%s\"", message));
    }
}
