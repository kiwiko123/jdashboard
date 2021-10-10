package com.kiwiko.webapp.streaming.kafka.api.exceptions;

public class SendKafkaMessageException extends JdashboardKafkaException {

    public SendKafkaMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
