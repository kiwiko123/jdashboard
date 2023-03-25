package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

public enum ConfigurationScopeLevel {
    // The configuration and its wired beans can only be referenced by classes in the same Java package.
    PRIVATE,

    // The configuration and its wired beans can be referenced by classes within the same Java package,
    // including child packages.
    PACKAGE,

    // The configuration and its wired beans can be referenced by classes located anywhere.
    PUBLIC
}
