package com.kiwiko.webapp.system.kafka.server.internal;

import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;
import com.kiwiko.webapp.system.kafka.server.api.KafkaServerService;

import javax.inject.Inject;

public class KafkaServerStartupHook implements StartupHook {

    @Inject private KafkaServerService kafkaServerService;

    @Override
    public void run() {
        kafkaServerService.startServer();
    }
}
