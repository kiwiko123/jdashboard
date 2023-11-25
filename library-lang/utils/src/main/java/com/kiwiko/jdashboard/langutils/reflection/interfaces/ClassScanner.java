package com.kiwiko.jdashboard.langutils.reflection.interfaces;

import java.util.Set;

public interface ClassScanner {

    Set<Class<?>> getClasses(String basePackage);
}
