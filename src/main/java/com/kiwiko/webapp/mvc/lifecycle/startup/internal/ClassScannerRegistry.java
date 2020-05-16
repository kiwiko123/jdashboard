package com.kiwiko.webapp.mvc.lifecycle.startup.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;
import com.kiwiko.webapp.mvc.resolvers.startup.RequestBodyParameterMethodArgumentValidator;

public class ClassScannerRegistry {

    private ClassScanner classScanner;

    public ClassScannerRegistry() {
        classScanner = new InjectManuallyConfigurer<ClassScanner>()
                .withBinding(LogService.class, ConsoleLogService.class)
                .withInstance(new ClassScanner())
                .create();
        buildRegistry();
    }

    public void buildRegistry() {
        classScanner.register(RequestBodyParameterMethodArgumentValidator.class);
    }

    public void run() {
        classScanner.process();
    }
}
