package com.kiwiko.mvc.lifecycle.startup.internal;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.impl.ConsoleLogService;
import com.kiwiko.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;

public class ClassScannerRegistry {

    private ClassScanner classScanner;

    public ClassScannerRegistry() {
        classScanner = new InjectManuallyConfigurer<ClassScanner>()
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(new ClassScanner())
                .create();
    }

    public void buildRegistry() {

    }

    public void run() {
        classScanner.process();
    }
}
