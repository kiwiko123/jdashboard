package com.kiwiko.webapp.streaming.kafka.playground;

import com.kiwiko.webapp.streaming.kafka.api.JdashboardKafkaProducer;
import com.kiwiko.webapp.streaming.kafka.api.exceptions.SendKafkaMessageException;
import com.kiwiko.webapp.streaming.kafka.api.parameters.ProducerSendMessageParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;

@Controller
public class KafkaPlaygroundController {

    @Inject private JdashboardKafkaProducer kafkaProducer;

    @GetMapping("/playground/api/kafka")
    public void test() {
        ProducerSendMessageParameters parameters = ProducerSendMessageParameters.newBuilder()
                .setTopicName("jdashboardTestTopic1")
                .setMessage("test")
                .build();
        try {
            kafkaProducer.sendMessage(parameters);
        } catch (SendKafkaMessageException e) {
            throw new RuntimeException(e);
        }
    }
}
