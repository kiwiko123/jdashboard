package com.kiwiko.webapp;

import com.kiwiko.webapp.mvc.lifecycle.startup.internal.ClassScannerRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        processClasses();
        SpringApplication.run(Application.class, args);
    }

    private static void processClasses() {
        ClassScannerRegistry registry = new ClassScannerRegistry();
        registry.buildRegistry();
        registry.run();
    }
}
