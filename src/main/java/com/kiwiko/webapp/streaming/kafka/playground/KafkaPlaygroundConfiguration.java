package com.kiwiko.webapp.streaming.kafka.playground;

import com.kiwiko.webapp.streaming.kafka.api.DefaultTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaPlaygroundConfiguration {

    @Bean
    public NewTopic topic1() {
        return new DefaultTopic("jdashboardTestTopic1");
    }
}
