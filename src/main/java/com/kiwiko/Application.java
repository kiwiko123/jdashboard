package com.kiwiko;

import com.kiwiko.library.metrics.api.Logger;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleService;
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
