package com.kiwiko.jdashboard.library.lang.reflection.api.interfaces;

import java.util.Set;

public interface ClassScanner {

    Set<Class<?>> getClasses(String basePackage);
}
