package com.kiwiko;

import com.kiwiko.mvc.lifecycle.ClassScannerRegistry;
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
