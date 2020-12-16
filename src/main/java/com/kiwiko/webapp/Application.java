package com.kiwiko.webapp;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ShutdownService;
import com.kiwiko.webapp.mvc.lifecycle.startup.internal.ClassScannerRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

@SpringBootApplication
public class Application {

    @Inject private LogService logService;
    @Inject private ShutdownService shutdownService;

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
        logService.debug("Setting up application");
    }

    @PreDestroy
    public void tearDown() {
        shutdownService.shutdown();
    }
}
