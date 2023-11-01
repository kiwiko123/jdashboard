package com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations;

public enum ConfigurationScopeLevel {
    // The configuration and its wired beans can only be dependency injected
    // by classes in the same Java package.
    PRIVATE,

    // The configuration and its wired beans can be dependency injected
    // by classes within the same Java package, including descendant packages.
    PACKAGE,

    // The configuration and its wired beans can be dependency injected
    // by classes located anywhere.
    PUBLIC
}
