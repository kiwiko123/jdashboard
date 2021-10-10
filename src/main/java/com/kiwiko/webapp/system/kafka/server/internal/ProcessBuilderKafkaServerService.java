package com.kiwiko.webapp.system.kafka.server.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.system.kafka.server.api.KafkaServerService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Singleton
public class ProcessBuilderKafkaServerService implements KafkaServerService {

    private @Nullable Process zookeeperProcess;
    private @Nullable Process kafkaServerProcess;
    @Inject private Logger logger;

    @Override
    public void startServer() {
        try {
            startZookeeper();
            startKafkaServer();
        } catch (IOException e) {
            logger.error("Error starting Kafka Server", e);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopServer() {
        if (zookeeperProcess != null) {
            zookeeperProcess.destroy();
            zookeeperProcess = null;
            logger.info("Stopped Zookeeper service");
        }

        if (kafkaServerProcess != null) {
            kafkaServerProcess.destroy();
            kafkaServerProcess = null;
            logger.info("Stopped Kafka server");
        }
    }

    private void startZookeeper() throws IOException {
        zookeeperProcess = createKafkaProcessBuilder("./bin/zookeeper-server-start.sh", "config/zookeeper.properties")
                .start();

        logger.info("Zookeeper service started");
    }

    private void startKafkaServer() throws IOException {
        kafkaServerProcess = createKafkaProcessBuilder("./bin/kafka-server-start.sh", "config/server.properties")
                .start();

        logger.info("Kafka server started");
    }

    private ProcessBuilder createKafkaProcessBuilder(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // TODO search for Kafka directory
        processBuilder.directory(new File("/Users/geoffreyko/Documents/git_workspace/jdashboard/kafka_2.13-3.0.0"));

        return processBuilder;
    }
}
