package com.kiwiko.webapp.system.kafka.server.internal;

import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;
import com.kiwiko.webapp.system.kafka.server.api.KafkaServerService;

import javax.inject.Inject;

public class KafkaServerShutdownHook implements ShutdownHook {

    @Inject private KafkaServerService kafkaServerService;

    @Override
    public boolean isEnabled() {
        return kafkaServerService.shouldAutoStartServers();
    }

    @Override
    public void run() {
        kafkaServerService.stopServer();
    }
}
