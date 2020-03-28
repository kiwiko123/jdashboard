package com.kiwiko.mvc.lifecycle.startup.internal;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.internal.ConsoleLogService;
import com.kiwiko.mvc.configuration.ConfigurationHelper;
import com.kiwiko.mvc.lifecycle.dependencies.data.DependencyBinding;
import com.kiwiko.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;

public class ClassScannerRegistry {

    private ClassScanner classScanner;

    public ClassScannerRegistry() {
        ConfigurationHelper configurationHelper = new ConfigurationHelper();
        DependencyBinding binding = new DependencyBinding(LogService.class, ConsoleLogService.class);
        this.classScanner = configurationHelper.createWithManualInjection(new ClassScanner(), binding);
    }

    public void buildRegistry() {

    }

    public void run() {
        classScanner.process();
    }
}
