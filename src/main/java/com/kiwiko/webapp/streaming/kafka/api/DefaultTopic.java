package com.kiwiko.webapp.streaming.kafka.api;

import org.apache.kafka.clients.admin.NewTopic;

public class DefaultTopic extends NewTopic {

    public DefaultTopic(String topicName) {
        super(topicName, 1, (short) 1);
    }
}
