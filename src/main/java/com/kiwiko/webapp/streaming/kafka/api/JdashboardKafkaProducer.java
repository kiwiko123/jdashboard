package com.kiwiko.webapp.streaming.kafka.api;

import com.kiwiko.webapp.streaming.kafka.api.exceptions.SendKafkaMessageException;
import com.kiwiko.webapp.streaming.kafka.api.parameters.ProducerSendMessageParameters;

public interface JdashboardKafkaProducer {

    void sendMessage(ProducerSendMessageParameters parameters) throws SendKafkaMessageException;
}
