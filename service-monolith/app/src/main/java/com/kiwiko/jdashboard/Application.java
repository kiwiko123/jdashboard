package com.kiwiko.jdashboard;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

@SpringBootApplication
public class Application {

    @Inject private LifeCycleService lifeCycleService;
    @Inject private Logger logger;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void setUp() {
        logger.info("Setting up application");
        lifeCycleService.startUp();
    }

    @PreDestroy
    public void tearDown() {
        logger.info("Shutting down application");
        lifeCycleService.shutDown();
    }
}
