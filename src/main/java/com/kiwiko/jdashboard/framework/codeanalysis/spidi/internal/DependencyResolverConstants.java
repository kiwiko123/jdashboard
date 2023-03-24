package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal;

import java.util.Set;

class DependencyResolverConstants {
    public static final Set<String> IGNORED_DEPENDENCY_INJECTING_CLASSES = Set.of(
            "com.kiwiko.jdashboard.Application");

    public static final Set<String> IGNORED_DEPENDENCY_CLASSES = Set.of(
            "com.fasterxml.jackson.databind.ObjectMapper");
}
