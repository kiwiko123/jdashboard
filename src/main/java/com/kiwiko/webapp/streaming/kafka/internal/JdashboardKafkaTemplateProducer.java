package com.kiwiko.webapp.streaming.kafka.internal;

import com.kiwiko.library.monitoring.logging.api.dto.Log;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.streaming.kafka.api.JdashboardKafkaProducer;
import com.kiwiko.webapp.streaming.kafka.api.exceptions.SendKafkaMessageException;
import com.kiwiko.webapp.streaming.kafka.api.parameters.OccurrenceType;
import com.kiwiko.webapp.streaming.kafka.api.parameters.ProducerSendMessageParameters;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class JdashboardKafkaTemplateProducer implements JdashboardKafkaProducer {

    @Inject private KafkaTemplate<String, String> kafkaTemplate;
    @Inject private Logger logger;

    @Override
    public void sendMessage(ProducerSendMessageParameters parameters) throws SendKafkaMessageException {
        Objects.requireNonNull(parameters);
        Objects.requireNonNull(parameters.getTopicName(), "Topic name is required");
        Objects.requireNonNull(parameters.getOccurrenceType(), "Occurrence type is required");

        try {
            sendMessageImpl(parameters);
        } catch (InterruptedException | ExecutionException e) {
            throw new SendKafkaMessageException(String.format("Error sending message with parameters %s", parameters), e);
        }
    }

    private void sendMessageImpl(ProducerSendMessageParameters parameters) throws InterruptedException, ExecutionException {
        ProducerRecord<String, String> record = new ProducerRecord<>(parameters.getTopicName(), parameters.getMessage());
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        logger.debug(String.format("Sent Kafka message %s", parameters.toString()));

        if (parameters.getOccurrenceType() == OccurrenceType.SYNCRHONOUS) {
            SendResult<String, String> result = future.get();
            logger.debug(result.toString());
        }
    }
}
