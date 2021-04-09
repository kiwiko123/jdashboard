package com.kiwiko;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleService;
import com.kiwiko.webapp.mvc.lifecycle.startup.internal.ClassScannerRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

@SpringBootApplication
public class Application {

    @Inject private LifeCycleService lifeCycleService;
    @Inject private LogService logService;

    public static void main(String[] args) {
        processClasses();
        SpringApplication.run(Application.class, args);
    }

    private static void processClasses() {
        ClassScannerRegistry registry = new ClassScannerRegistry();
        registry.buildRegistry();
        registry.run();
    }

    @PostConstruct
    public void setUp() {
        logService.info("Setting up application");
        lifeCycleService.startUp();
    }

    @PreDestroy
    public void tearDown() {
        logService.info("Shutting down application");
        lifeCycleService.shutDown();
    }
}
