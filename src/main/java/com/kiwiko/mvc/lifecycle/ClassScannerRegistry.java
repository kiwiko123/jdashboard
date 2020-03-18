package com.kiwiko.mvc.lifecycle;

public class ClassScannerRegistry {

    private ClassScanner classScanner;

    public ClassScannerRegistry() {
        this.classScanner = new ClassScanner();
    }

    public void buildRegistry() {

    }

    public void run() {
        classScanner.process();
    }
}
