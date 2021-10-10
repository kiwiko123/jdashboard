# Kafka and Jdashboard
Jdashboard provides a loose API to set up Kafka producers and consumers.

## Instructions
### 1. Create a topic.
Topics are programmatically created via configuration files.

```java
// MyTopicConfiguration.java

import com.kiwiko.webapp.streaming.kafka.api.DefaultTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyTopicConfiguration {

    @Bean
    public NewTopic myTopic() {
        return new DefaultTopic("myTopic");
    }
}
```

### 2. Create a producer.

```java
// MyTopicProducer.java

import com.kiwiko.webapp.streaming.kafka.api.JdashboardKafkaProducer;
import com.kiwiko.webapp.streaming.kafka.api.parameters.ProducerSendMessageParameters;

import javax.inject.Inject;

public class MyTopicProducer {
    @Inject private JdashboardKafkaProducer kafkaProducer;

    public void send(String message) {
        ProducerSendMessageParameters parameters = ProducerSendMessageParameters.newBuilder()
                .setTopicName("myTopic")
                .setMessage(message)
                .build();
        
        kafkaProducer.sendMessage(parameters);
    }
}
```

### 3. Create a consumer.

```java
// MyTopicConsumer.java

import org.springframework.kafka.annotation.KafkaListener;

public class MyTopicConsumer {

    @KafkaListener(topics = "myTopic")
    public void consumeMyTopic(String message) {
        System.out.printf("Received myTopic message %s\n", message);
    }
}
```